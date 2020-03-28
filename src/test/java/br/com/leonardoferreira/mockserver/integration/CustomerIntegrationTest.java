package br.com.leonardoferreira.mockserver.integration;

import br.com.leonardoferreira.mockserver.MockServer;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CustomerIntegrationTest {

    @BeforeAll
    static void beforeAll() {
        MockServer.start();
    }

    @AfterEach
    void tearDown() {
        MockServer.clearHandlers();
    }

    @AfterAll
    static void afterAll() {
        MockServer.stop();
    }

    @Test
    void shouldReturnSimpleResponse() {
        MockServer.addHandler(new FindCustomerByIdHandler());

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                    .header("x-details", "true")
                .when()
                    .get("/customer/123")
                .then()
                    .log().all()
                    .header("x-custom-header", Matchers.is("customvalue"))
                    .statusCode(200)
                    .body("name", Matchers.is("Mario"));
        // @formatter:on
    }

    @Test
    void shouldFindAll() {
        MockServer.addHandler(new FindAllCustomerHandler());

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                    .queryParam("name", "mario")
                    .queryParam("lastName", "armario")
                .when()
                    .get("/customer")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("[0].name", Matchers.is("mario"));
        // @formatter:on
    }

}
