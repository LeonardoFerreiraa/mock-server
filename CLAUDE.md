# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build and run all checks (PMD + Checkstyle run at validate phase)
./mvnw verify

# Run tests only
./mvnw test

# Run a single test class
./mvnw test -Dtest=CustomerIntegrationTest

# Run a single test method
./mvnw test -Dtest=CustomerIntegrationTest#shouldReturnSimpleResponse

# Skip static analysis (PMD + Checkstyle) during development
./mvnw test -Dmaven.pmd.skip=true -Dcheckstyle.skip=true
```

Static analysis (PMD and Checkstyle) runs at the `validate` phase — it fires before compilation on every `./mvnw` invocation. Rules live in `code-style/pmd-ruleset.xml` and `code-style/checkstyle.xml`.

## Architecture

This is a Java 8 library (published to Maven Central) that provides an embeddable HTTP mock server for use in tests. No Spring or framework dependency — uses `com.sun.net.httpserver.HttpServer` under the hood.

### Request flow

```
MockServer (static facade)
  └─ RequestDispatcher       — holds list of RequestHandlerDecorators
       └─ RequestHandlerDecorator  — wraps user's RequestHandler, captures journal
            └─ RequestMatcher      — chain: method → URN → query params → headers
```

1. `MockServer.addHandler(handler)` wraps the handler in `RequestHandlerDecorator` and returns a `RequestHandlerJournal` for assertions.
2. On each HTTP request, `RequestDispatcher.dispatch` finds the first matching decorator via `RequestMatcher` and calls its `handle`.
3. If no handler matches, returns 404. If handler throws, returns 500.
4. Every request/response pair is recorded in the handler's `RequestHandlerJournal`.

### Key types

- **`RequestHandler`** — interface consumers implement: `route()` returns a `Route`, `handle(Request)` returns a `Response`.
- **`Route`** — built via `Route.builder()`. Specifies `HttpMethod`, URL pattern, optional `QueryParam`s and `Header`s to match against.
- **`RequestMatcher`** — chain-of-responsibility pattern matching method → URN → query params → headers. `UrnMatcher` supports path variable patterns (e.g. `/customer/{id}`).
- **`RequestHandlerJournal`** — records all requests handled; exposes `requestCount()`, `firstRequest()`, `requests()`, `entries()` for test assertions.
- **`Request`** — exposes `bodyAs(Class)` (Jackson deserialization), `findHeader(name)`, `findQueryParam(name)`, `getUrn()`, `getMethod()`.
- **`Response`** — built via `Response.builder()` with `status`, `body` (serialized as JSON), and `headers`.

### Test pattern

Tests start/stop `MockServer` in `@BeforeAll`/`@AfterAll` and call `MockServer.cleanHandlers()` in `@AfterEach`. Handlers are registered per-test and verified via `RequestHandlerJournal`. Integration tests use REST-assured against `localhost:8080`.

`MockServer` is a static singleton — concurrent test parallelism will conflict. Tests must run single-threaded.
