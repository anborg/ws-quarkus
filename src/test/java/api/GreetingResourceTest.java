package api;

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

    //@Test //Redist tests are integration
    public void testRedisGet() {
        given()
                .when().get("/api/v1/redis/set/team/myteam")
                .then()
                .statusCode(200)
                .body(is("49ers"));
    }

}