# Tasks: json-file-based-mocks

- [x] Create `MockDefinition` POJO with nested `RouteDefinition` and `ResponseDefinition` in package `br.com.leonardoferreira.mockserver.loader`
- [x] Create `JsonMockLoader` class that reads classpath resource, parses `MockDefinition`, builds `Route` + `Response`, wraps in anonymous `RequestHandler`, and delegates to `MockServer.addHandler()`
- [x] Add `MockServer.load(String classpathPath)` static method delegating to `JsonMockLoader`
- [x] Create JSON mock files in `src/test/resources/mocks/` for integration test scenarios
- [x] Write `JsonMockLoaderIntegrationTest` covering all spec scenarios (load valid file, file not found, malformed JSON, headers matching, query params matching, path variables, JSON body, string body, no body, custom response headers, journal assertions)
