package api;

import service.ServiceException;

import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

class ApiUtil {
    static Response badRequest(String err) {
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), err).build();
    }

    static Response notFound() {
        return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
    }
    static Response serverError(String err) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), err).build();
    }
    public static class workorder {//deliberately lowercase
        public static final String newObject =
                "{\n" +
                        "  \"servicePointId\": 123,\n" +
                        "  \"eamId\": \"222\",\n" +
                        "  \"createDate\": \"2021-10-30T15:35:56.452Z\",\n" +
                        "  \"dispatchDate\": \"2021-10-30T15:35:56.452Z\",\n" +
                        "  \"dispatchGroupCode\": \"ORG2\",\n" +
                        "  \"meterWorkTypeCode\": \"AA\",\n" +
                        "  \"meterServiceProjectCode\": \"S\",\n" +
                        "  \"comments\": \"my comment\",\n" +
                        "  \"instructions\": \"my instruction\",\n" +
                        "  \"status\": \"N\"\n" +
                        "}"
                ;
        public static final String newInvalidObject =
                "{\n" +
                        "  \"id\": 123,\n" +
                        "  \"servicePointId\": 123,\n" +
                        "  \"eamId\": \"222\",\n" +
                        "  \"createDate\": \"2021-10-30T15:35:56.452Z\",\n" +
                        "  \"dispatchDate\": \"2021-10-30T15:35:56.452Z\",\n" +
                        "  \"dispatchGroupCode\": \"ORG2\",\n" +
                        "  \"meterWorkTypeCode\": \"AA\",\n" +
                        "  \"meterServiceProjectCode\": \"S\",\n" +
                        "  \"comments\": \"my comment\",\n" +
                        "  \"instructions\": \"my instruction\",\n" +
                        "  \"status\": \"N\"\n" +
                        "}"
                ;
    }

}
