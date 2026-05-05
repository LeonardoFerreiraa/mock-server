## ADDED Requirements

### Requirement: Load mock from classpath JSON file
The system SHALL provide `MockServer.load(String classpathPath)` that reads a `.json` mock definition file from the classpath, registers it as a handler, and returns a `RequestHandlerJournal`.

#### Scenario: Load valid mock file
- **WHEN** `MockServer.load("mocks/find-customer.json")` is called with a valid file on classpath
- **THEN** a `RequestHandlerJournal` is returned and the mock is registered and active

#### Scenario: File not found on classpath
- **WHEN** `MockServer.load("mocks/nonexistent.json")` is called with a path that does not exist on classpath
- **THEN** an `IllegalArgumentException` is thrown with a message that includes the path

#### Scenario: Malformed JSON file
- **WHEN** `MockServer.load(path)` is called with a file containing invalid JSON
- **THEN** a `RuntimeException` is thrown indicating the parse failure

### Requirement: JSON mock file schema — route
The `.json` mock file SHALL support route matching via `route` object with fields: `method` (required), `url` (required), `headers` (optional map), `queryParams` (optional map).

#### Scenario: Route with method and URL only
- **WHEN** a file defines only `route.method` and `route.url`
- **THEN** the mock matches requests by method and URL, ignoring headers and query params

#### Scenario: Route with headers matching
- **WHEN** a file defines `route.headers` with key-value pairs
- **THEN** the mock only matches requests that contain all specified headers with matching values

#### Scenario: Route with query params matching
- **WHEN** a file defines `route.queryParams` with key-value pairs
- **THEN** the mock only matches requests that contain all specified query params with matching values

#### Scenario: URL with path variable
- **WHEN** `route.url` contains a `{variable}` pattern (e.g., `/customer/{id}`)
- **THEN** the mock matches requests where the variable segment is any value

### Requirement: JSON mock file schema — response
The `.json` mock file SHALL support response definition via `response` object with fields: `status` (required), `headers` (optional map), `body` (optional).

#### Scenario: Response with JSON object body
- **WHEN** `response.body` is a JSON object
- **THEN** the response body is serialized as JSON and `Content-Type` is `application/json`

#### Scenario: Response with string body
- **WHEN** `response.body` is a JSON string (e.g., `"pong"`)
- **THEN** the response body is the raw string and `Content-Type` is `text/plain`

#### Scenario: Response with no body
- **WHEN** `response.body` is absent
- **THEN** the response has an empty body

#### Scenario: Response with custom headers
- **WHEN** `response.headers` defines key-value pairs
- **THEN** those headers are present in the HTTP response

### Requirement: Journal from loaded mock is functional
The `RequestHandlerJournal` returned by `MockServer.load()` SHALL behave identically to one returned by `MockServer.addHandler()`.

#### Scenario: Journal records matched request
- **WHEN** a request matches a loaded mock and `journal.requestCount()` is called
- **THEN** the count reflects the number of matched requests

#### Scenario: Journal exposes first request
- **WHEN** at least one request has matched and `journal.firstRequest()` is called
- **THEN** the first matched `Request` is returned with correct headers, params, and URN
