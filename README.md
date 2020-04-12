# Mock Server

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/br.com.leonardoferreira/mock-server/badge.png)](https://search.maven.org/artifact/io.github.openfeign/feign-core/)

Mock server is a configurable web server for testing http calls

---

## Why another mock server?

We already have a lot of mock servers, but them rarely offers a good level of reuse for stubs. In an attempt to solve this problem, we create a new mock server which the focus is exactly this, reuse of stubs.

## How does it work / How we try to solve reuse the problem?

Instead of providing a fluent api to build a stub, the stubbing is done by providing an implementation of the `RequestHandler` interface, which was two methods:
`route`, which tells when this stub will be used, and `handle` which tells how the request will be handled to return a response.

# Usage

## Dependency

Maven:
```xml
<dependency>
    <groupId>br.com.leonardoferreira</groupId>
    <artifactId>mock-server</artifactId>
    <version>${mock-server.version}</version>
    <scope>test</scope>
</dependency>    
```

Gradle:
```groovy
testImplementation("br.com.leonardoferreira:mock-server:${mockServerVersion}")
```

## Setting up the server

Before starting the server, some settings can be made. To do this, we can use the static methods on the `MockServer` class.

Example:

```java
MockServer.port(8888); // the default is 8080
MockServer.basePath("/my-server"); // the default is "/"
MockServer.hostname("127.0.0.1"); // the default is "localhost"
```

We recommend using the default values and replace them only if you really need.

## Starting and stopping the server

To start the server, we just need to call the start method on the `MockServer` class.

Example:

```java
MockServer.start();
```

The same applies to stop, example:

```java
MockServer.stop();
```

## Stubbing

### Adding RequestHandler

To teach the server to respond to a request, you need to add a handler to the server.

Example:

```java
MockServer.addHandler(new MyCustomRequestHandler()); 
```

### Creating your own RequestHandler

To create an implementation of `RequestHandler` interface, we need to provide two methods: `route`, which tells when this stub will be used, and `handle` which tells how the request will be handled to return a response.

Example:

```java
public class MyCustomRequestHandler implements RequestHandler {

    @Override
    public Route route() {
        return Route.builder()
                .method(HttpMethod.GET)
                .url("/my-path")
                .build();
    }

    @Override
    public Response handle(final Request request) {
        return Response.builder()
                .status(200)
                .body(Map.of("greeting", "Hello world"))
                .build();
    }    

}
```

Providing this `RequestHandler` all calls on `http://localhost:8080/my-path`, will return a response with status 200 ok, and the following body:

```json
{
   "greeting": "Hello world"
}
``` 

### Route

`Route` is the object that will be used to match when the request is eligible to be handled by certain` RequestHandler`.

In `Route.builder()`, we can add parameters that will be used to match. Full example:

```java
Route.builder()
    .method(HttpMethod.GET) 
    .url("/my-path")
    .queryParams(
        QueryParam.of("myParamName", "myParamValue")
    )
    .headers(
        Header.of("x-my-header", "x-my-header-value")
    )
    .build();
```

### Response

Response is the object that will be sent to the server and will be used to create the HTTP response.

Example:

```java
Response.builder()
        .status(200)
        .body(new CustomObjectResponse("str", 1))
        .headers(
                Header.of("x-custom-response-header", "customValue")
        )
        .build();
```

## Verifying

All the asserts can be made using `RequestHandlerJournal` which will be returned when a new `RequestHandler` is added.

Example:

```java
@Test
void shouldTestSomething() {
    final RequestHandlerJournal myCustomRequestHandlerJournal = MockServer.addHandler(new MyCustomRequestHandler());

    // ...

    assertEquals(1, myCustomRequestHandlerJournal.requestCount());

    final Request request = myCustomRequestHandlerJournal.firstRequest();
    // some others asserts with first request ...

    final CustomRequest cr = request.bodyAs(CustomRequest.class);
    // some others asserts with request body...
}
```

## Additional notes

- Both bodies, request body and response body, will always be treated as json;