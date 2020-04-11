package br.com.leonardoferreira.mockserver.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.leonardoferreira.mockserver.MockServer;
import br.com.leonardoferreira.mockserver.RequestHandlerJournal;
import br.com.leonardoferreira.mockserver.entity.Header;
import br.com.leonardoferreira.mockserver.entity.QueryParam;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.integration.domain.Customer;
import br.com.leonardoferreira.mockserver.integration.requesthandler.CreateCustomerRequestHandler;
import br.com.leonardoferreira.mockserver.integration.requesthandler.FindAllCustomerRequestHandler;
import br.com.leonardoferreira.mockserver.integration.requesthandler.FindCustomerByIdRequestHandler;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
        MockServer.cleanHandlers();
    }

    @AfterAll
    static void afterAll() {
        MockServer.stop();
    }

    @Test
    void shouldReturnSimpleResponse() {
        final RequestHandlerJournal findCustomerByIdJournal = MockServer.addHandler(new FindCustomerByIdRequestHandler());

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

        assertEquals(1, findCustomerByIdJournal.requestCount());
        final Request request = findCustomerByIdJournal.firstRequest();
        assertEquals(Header.of("x-details", "true"), request.findHeader("x-details"));
    }

    @Test
    void shouldFindAll() {
        final RequestHandlerJournal findAllCustomerHandlerJournal = MockServer.addHandler(new FindAllCustomerRequestHandler());

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

        assertEquals(1, findAllCustomerHandlerJournal.requestCount());
        final Request request = findAllCustomerHandlerJournal.firstRequest();
        assertEquals(QueryParam.of("name", "mario"), request.findQueryParam("name"));
        assertEquals(QueryParam.of("lastName", "armario"), request.findQueryParam("lastName"));
    }

    @Test
    void shouldReceiveRequestWithBody() {
        final RequestHandlerJournal createCustomerRequestHandlerJournal = MockServer.addHandler(new CreateCustomerRequestHandler());

        // @formatter:off
        RestAssured
                .given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(new Customer("mario"))
                .when()
                    .post("/customer")
                .then()
                    .log().all()
                    .statusCode(201);
        // @formatter:on

        assertEquals(1, createCustomerRequestHandlerJournal.requestCount());
        final Request request = createCustomerRequestHandlerJournal.firstRequest();
        final Customer customer = request.bodyAs(Customer.class);
        assertEquals("mario", customer.getName());
    }

}
