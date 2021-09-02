package caas;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello Quarkus"));
    }

    @Test //Redist tests are integration
    public void testRedisGet() {
        given()
                .when().get("/caas/redis/api/v1/set/team/caasden")
                .then()
                .statusCode(200)
                .body(is("caasden"));
    }

}