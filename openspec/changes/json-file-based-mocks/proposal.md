## Why

The Java DSL requires users to implement a `RequestHandler` interface per mock, creating one class per route even for static responses. JSON files make mocks shareable across tests, easier to read, and faster to write for the common case.

## What Changes

- Add `MockServer.load(String classpathPath)` — loads a `.json` mock file from classpath and returns `RequestHandlerJournal`
- Mock files define route matching (method, url, headers, queryParams) and response (status, headers, body) in JSON
- Body field: object node → serialized as `application/json`; string → returned as `text/plain`
- Java DSL (`RequestHandler` interface) is unchanged — remains the escape hatch for dynamic/logic-based responses

## Capabilities

### New Capabilities

- `json-mock-loader`: Parse and load `.json` mock definition files from classpath into `RequestHandlerDecorator` instances, returning a `RequestHandlerJournal`

### Modified Capabilities

<!-- none -->

## Impact

- `MockServer` facade: new `load(String)` static method
- New internal: `JsonMockLoader` (or equivalent) that parses JSON and constructs `Route` + `Response`
- No changes to `RequestHandler`, `Route`, `Response`, `RequestMatcher`, or `RequestHandlerJournal`
- Dependency: Jackson already present (used by `Request.bodyAs`)
- No breaking changes — existing Java DSL usage unaffected
