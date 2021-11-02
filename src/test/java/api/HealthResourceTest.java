package api;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static test.TestUtil.auth.givenBasicAuth;

@QuarkusTest
@TestHTTPEndpoint(HealthResource.class)
public class HealthResourceTest {

    @Test
    public void noauth_hello_must_succeed() {
        given()
          .when().get("/")
          .then()
             .statusCode(200)
             .body(is("Hello Anon"));
    }
    @Test
    public void auth_hello_must_succeed() {
        givenBasicAuth()
                .when().get("secure")
                .then()
                .statusCode(200)
                .body(startsWith("Secure-hello to user"));
    }

    //@Test //Redist tests are integration
    public void testRedisGet() {
        given()
                .when().get("/api/v1/redis/set/team/myteam")
                .then()
                .statusCode(200)
                .body(is("49ers"));
    }

}