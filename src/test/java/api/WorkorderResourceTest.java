package api;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import model.PageRequest;
import model.Workorder;
import org.junit.jupiter.api.Test;
import test.TestUtil;
import util.Util;

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
    public void happy_path_workorder_should_succeed() {
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

        //Actionable: get
        var sinceDate = Util.now_yyyy_MM_dd();
        var page = new PageRequest(); page.pageNum=1;
        Response resp_get_actionable= given().header("Content-type", MediaType.APPLICATION_JSON)
                .and().pathParams("createDate",sinceDate)
                .and().queryParam("pageNum", page.pageNum)
                .when().get("actionable/since/{createDate}")
                .then().extract().response();
        log.info("GET actionable response: " +resp_get_actionable.asPrettyString());
        assertEquals(Status.OK.getStatusCode(), resp_get_actionable.statusCode());
        assertEquals(page.pageNum,resp_get_actionable.jsonPath().getInt("pageNum"));

    }

    @Test
    public void post_should_reject_invalid_wo_withid_addDt_modDt(){
        final var in = TestUtil.workorder.build4Create();

        {//Invalid to set id
            in.id = 123L;
            Response post_resp = getPostResponse(in);
            assertEquals(Status.BAD_REQUEST.getStatusCode(), post_resp.statusCode());
            in.id = null;
        }
        {//Test: addDt -- should not be present
            in.addDate = Util.nowInstant();
            Response post_resp = getPostResponse(in);
            assertEquals(Status.BAD_REQUEST.getStatusCode(), post_resp.statusCode());
            in.addDate = null;
        }
        {//Test: modDt -- should not be present
            in.modDate = Util.nowInstant();
            Response post_resp = getPostResponse(in);
            assertEquals(Status.BAD_REQUEST.getStatusCode(), post_resp.statusCode());
            in.modDate = null;
        }
    }

    private Response getPostResponse(Workorder in){
        Response post_resp = given()
                .header("Content-type", MediaType.APPLICATION_JSON)
                .and().body(in)
                .when().post()
                .then().extract().response();
        return post_resp;
    }

    @Test
    public void shoud_say_not_found(){
        final long NONEXISTANT_WORKORDER_ID = 9999L;
        Response resp = getWorkOrderResp(NONEXISTANT_WORKORDER_ID);
        assertEquals(Status.NOT_FOUND.getStatusCode(), resp.statusCode());
    }

    @Test
    public void get_actionable_should_fail_for_dates_beyond_window(){
        var sinceDate = Util.to_yyyy_MM_dd(Util.nowLocalDate().minusMonths(7));
        var page = new PageRequest(); page.pageNum=1;
        Response resp_get_actionable= given().header("Content-type", MediaType.APPLICATION_JSON)
                .and().pathParams("createDate",sinceDate)
                .and().queryParam("pageNum", page.pageNum)
                .when().get("actionable/since/{createDate}")
                .then().extract().response();
        log.info("GET actionable response: " +resp_get_actionable.asPrettyString());
        assertEquals(Status.BAD_REQUEST.getStatusCode(), resp_get_actionable.statusCode());

    }

    private Response getWorkOrderResp(Long id) {
        Response getResponse = given()
                .header("Content-type", MediaType.APPLICATION_JSON)
                .and().pathParam("id", id)
                .when().get("{id}")
                .then()
                .extract().response();
        log.info("GET response: " +getResponse.asPrettyString());
//        assertEquals(Status.OK.getStatusCode(), getResponse.statusCode());
//        Long id2= getResponse.jsonPath().getLong("id");
//        assertNotNull(id2);
//        assertEquals(id, id2);
        return getResponse;
    }
}