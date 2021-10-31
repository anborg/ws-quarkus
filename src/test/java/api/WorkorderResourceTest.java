package api;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import test.TestUtil;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.*;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@QuarkusTest
@TestHTTPEndpoint(WorkorderResource.class)
public class WorkorderResourceTest {
    private static final Logger log = Logger.getLogger(WorkorderResourceTest.class.getName());

    @Test
    public void post_get_Workorder() {
        final var in = TestUtil.workorder.build4Create();
        //Create : expect new obj & id & addDt
        Response post_resp = given()
                .header("Content-type", MediaType.APPLICATION_JSON)
                .and().body(in)
                .when().post()
                .then().extract().response();
        log.info("POST response: " +post_resp.asPrettyString());
        assertEquals(Status.CREATED.getStatusCode(), post_resp.statusCode());
        assertNotNull(post_resp.jsonPath().getLong("id"));

        Long id = post_resp.jsonPath().getLong("id");
        Response resp_after_post = getWorkOrderResp(id);
        Long id2= resp_after_post.jsonPath().getLong("id");
        String addDt =  resp_after_post.jsonPath().getString("addDate");//Instant.class did not work
        assertEquals(id, id2);
//        assertNotNull(addDt);

        //Update: eamId - expect new val, & modDt
        var eamId = "919";
        Response resp_put_eamid = given().header("Content-type", MediaType.APPLICATION_JSON)
                .and().pathParams("id", id, "eamId", eamId)
                .when().patch("{id}/eam/{eamId}")
                .then()
                .extract().response();
        log.info("PUT response: " +resp_put_eamid.asPrettyString());
        assertEquals(Status.OK.getStatusCode(), resp_put_eamid.statusCode());
        //get again
        Response get_after_put_eam = getWorkOrderResp(id);
        String eamId2= get_after_put_eam.jsonPath().getString("eamId");
        assertEquals(eamId,eamId2);
        String modDt =  get_after_put_eam.jsonPath().getString("modDate");
//        assertNotNull(modDt);

        //Update: Status
        var status = "P";
        Response resp_patch_status= given().header("Content-type", MediaType.APPLICATION_JSON)
                .and().pathParams("id", id, "status", status)
                .when().patch("{id}/status/{status}")
                .then().extract().response();
        log.info("PUT response: " +resp_put_eamid.asPrettyString());
        assertEquals(Status.OK.getStatusCode(), resp_patch_status.statusCode());
        //get again
        Response get_after_patch_status = getWorkOrderResp(id);
        assertEquals(status,get_after_patch_status.jsonPath().getString("status"));

    }

    private Response getWorkOrderResp(Long id) {
        Response getResponse = given()
                .header("Content-type", MediaType.APPLICATION_JSON)
                .and().pathParam("id", id)
                .when().get("{id}")
                .then()
                .extract().response();
        log.info("GET response: " +getResponse.asPrettyString());
        assertEquals(Status.OK.getStatusCode(), getResponse.statusCode());
        Long id2= getResponse.jsonPath().getLong("id");
        assertNotNull(id2);
        assertEquals(id, id2);
        return getResponse;
    }
}