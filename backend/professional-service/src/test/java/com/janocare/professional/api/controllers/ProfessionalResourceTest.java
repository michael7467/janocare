package api.controllers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ProfessionalResourceTest {

    @Test
    void shouldRegisterProfessional() {
        given()
                .contentType("application/json")
                .body("""
                {
                  "firstName": "John",
                  "lastName": "Doe"
                }
                """)
                .when()
                .post("/professionals")
                .then()
                .statusCode(201)
                .header("Location", containsString("/professionals/"));
    }

    @Test
    void shouldRejectInvalidProfessional() {
        given()
                .contentType("application/json")
                .body("""
                {
                  "firstName": "",
                  "lastName": "Doe"
                }
                """)
                .when()
                .post("/professionals")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturnNotFoundForUnknownProfessional() {
        UUID unknownId = UUID.randomUUID();

        given()
                .when()
                .get("/professionals/" + unknownId)
                .then()
                .statusCode(404)
                .body("code", equalTo("NOT_FOUND"));
    }
}