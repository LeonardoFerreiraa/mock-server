package br.com.leonardoferreira.mockserver.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.leonardoferreira.mockserver.MockServer;
import br.com.leonardoferreira.mockserver.RequestHandlerJournal;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JsonMockLoaderIntegrationTest {

    @BeforeAll
    static void beforeAll() {
        MockServer.start();
    }

    @AfterEach
    void tearDown() {
        MockServer.cleanHandlers();
    }

    @AfterAll
    static void afterAll() {
        MockServer.stop();
    }

    @Test
    void shouldLoadValidMockFileAndReturnJsonBody() {
        final RequestHandlerJournal journal = MockServer.load("mocks/get-customer.json");

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                .when()
                    .get("/mock-customer/123")
                .then()
                    .log().all()
                    .statusCode(200)
                    .header("x-custom-header", Matchers.is("customvalue"))
                    .body("name", Matchers.is("Mario"))
                    .body("id", Matchers.is(123));
        // @formatter:on

        assertEquals(1, journal.requestCount());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFileNotFound() {
        final IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> MockServer.load("mocks/nonexistent.json")
        );
        assertEquals(true, ex.getMessage().contains("mocks/nonexistent.json"));
    }

    @Test
    void shouldThrowRuntimeExceptionForMalformedJson() {
        assertThrows(
                RuntimeException.class,
                () -> MockServer.load("mocks/malformed.json")
        );
    }

    @Test
    void shouldMatchRouteWithPathVariable() {
        final RequestHandlerJournal journal = MockServer.load("mocks/get-customer-by-id.json");

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                .when()
                    .get("/mock-customer/999")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("name", Matchers.is("PathVariable"));
        // @formatter:on

        assertEquals(1, journal.requestCount());
        assertEquals("/mock-customer/999", journal.firstRequest().getUrn());
    }

    @Test
    void shouldMatchRouteWithRequiredHeaders() {
        MockServer.load("mocks/get-with-headers.json");

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                    .header("x-tenant", "acme")
                .when()
                    .get("/mock-header-check")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("ok", Matchers.is(true));
        // @formatter:on
    }

    @Test
    void shouldReturn404WhenHeaderDoesNotMatch() {
        MockServer.load("mocks/get-with-headers.json");

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                .when()
                    .get("/mock-header-check")
                .then()
                    .log().all()
                    .statusCode(404);
        // @formatter:on
    }

    @Test
    void shouldMatchRouteWithRequiredQueryParams() {
        MockServer.load("mocks/get-with-query-params.json");

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                    .queryParam("status", "active")
                .when()
                    .get("/mock-query-check")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("found", Matchers.is(true));
        // @formatter:on
    }

    @Test
    void shouldReturn404WhenQueryParamDoesNotMatch() {
        MockServer.load("mocks/get-with-query-params.json");

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                    .queryParam("status", "inactive")
                .when()
                    .get("/mock-query-check")
                .then()
                    .log().all()
                    .statusCode(404);
        // @formatter:on
    }

    @Test
    void shouldReturnStringBodyAsPlainText() {
        MockServer.load("mocks/ping.json");

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                .when()
                    .get("/mock-ping")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body(Matchers.is("pong"));
        // @formatter:on
    }

    @Test
    void shouldReturnEmptyBodyWhenBodyAbsent() {
        MockServer.load("mocks/no-body.json");

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                .when()
                    .delete("/mock-delete")
                .then()
                    .log().all()
                    .statusCode(204);
        // @formatter:on
    }

    @Test
    void shouldRecordRequestsInJournal() {
        final RequestHandlerJournal journal = MockServer.load("mocks/get-customer.json");

        RestAssured.get("/mock-customer/123");
        RestAssured.get("/mock-customer/123");

        assertEquals(2, journal.requestCount());
    }

}
