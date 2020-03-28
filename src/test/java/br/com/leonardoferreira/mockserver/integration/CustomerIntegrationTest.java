package br.com.leonardoferreira.mockserver.integration;

import br.com.leonardoferreira.mockserver.MockServer;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class CustomerIntegrationTest {

    @Test
    void shouldReturnSimpleResponse() {
        final MockServer mockServer = MockServer.create()
                .addHandler(new FindCustomerByIdHandler())
                .start();

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

        mockServer.stop();
    }

    @Test
    void shouldFindAll() {
        final MockServer mockServer = MockServer.create()
                .addHandler(new FindAllCustomerHandler())
                .start();

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

        mockServer.stop();
    }

}
