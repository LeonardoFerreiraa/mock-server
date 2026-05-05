## Context

`MockServer` currently supports only Java DSL — users implement `RequestHandler` per mock. Static mocks (no logic in `handle()`) represent the majority of use cases but require boilerplate class files. Jackson is already a dependency (used by `Request.bodyAs`). The existing `RequestHandlerDecorator` / `RequestMatcher` pipeline can be reused without modification.

## Goals / Non-Goals

**Goals:**
- Add `MockServer.load(String classpathPath)` that parses a `.json` file and registers a handler
- Support full route matching: method, url (with `{variable}` patterns), headers, queryParams
- Support response: status, headers, body (object → JSON, string → text/plain)
- Return `RequestHandlerJournal` from `load()` — same contract as `addHandler()`

**Non-Goals:**
- Multiple routes per file
- YAML or Markdown format support
- Dynamic body templating (e.g., echo path variables into response)
- Replacing the Java DSL — it stays as-is

## Decisions

### 1. `MockServer.load()` delegates to a `JsonMockLoader`

`MockServer.load(path)` reads the file from classpath, deserializes it via Jackson into an intermediate `MockDefinition` POJO, constructs a `Route` and `Response`, wraps them in an anonymous `RequestHandler`, and calls the existing `addHandler()`.

**Rationale:** Reuses `addHandler()` entirely — journal creation, dispatcher registration, and matching all stay unchanged.

**Alternative considered:** New code path bypassing `addHandler()`. Rejected — duplicates registration logic.

### 2. `MockDefinition` as intermediate POJO

Parse JSON into `MockDefinition { RouteDefinition route; ResponseDefinition response; }` using Jackson. Then translate to domain objects (`Route`, `Response`).

**Rationale:** Decouples JSON schema from domain model. Domain objects can evolve independently.

### 3. Body type detection: `JsonNode`

Deserialize `body` as `JsonNode`. If `isTextual()` → return as `text/plain`. If object/array → serialize back to JSON string and return as `application/json`.

**Rationale:** Natural Jackson distinction, no extra flags needed in the file.

### 4. Classpath resolution via `ClassLoader.getResourceAsStream()`

**Rationale:** Standard Java 8 pattern, works in all test runners (JUnit 4/5, Maven Surefire).

## Risks / Trade-offs

- **Missing file at runtime** → `IllegalArgumentException` with clear message including the path. No silent null.
- **Malformed JSON** → Jackson `JsonProcessingException` propagates with file path context.
- **`{variable}` in url** → Already handled by `UrnMatcher` — no new logic needed.
- **No dynamic responses** → By design. Users fall back to Java DSL. This is a feature, not a gap.

## Migration Plan

Additive change only. No existing code modified. No migration required.
