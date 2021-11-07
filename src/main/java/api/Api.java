package api;

import javax.ws.rs.core.Response;

public final class Api {
    private static final String base = "/api/v1/asset/";
    public static class res{
//        static final APIResponse bad_request ;
        public static class bad_req{
            static final String code = "400";
            static final String desc = "Bad Request";
        }
        public static class server_err{
            static final String code = "500";
            static final String desc = "Server Error";
        }
        public static class ok{
            static final String code = "200";
            static final String desc = "OK";
        }
        public static class created{
            static final String code = "201";
            static final String desc = "Created";
        }
        public static class not_found{
            static final String code = "404";
            static final String desc = "Not Found";
        }
    }
    public static class health{
        static final String tag = "health";
        static final String url = base + tag ;
    }
    public static class workorders {
        static final String tag = "workorders";
        static final String url = base + tag ;
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
    }
    public static class users {
        static final String tag = "users";
        static final String url = base + tag ;
    }
    public static class  adminuser{
        public static final String testuser = "adminuser";
        public static final String testpassword = "adminuser123";
        public static final String role = "adminuser";
    }
    public static class  apiuser{
        public static final String testuser = "luci";
        public static final String testpassword = "luci123";
        public static final String role = "apiuser";
    }

    static Response badRequest(String err) {
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), err).build();
    }

    static Response notFound() {
        return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
    }
    static Response serverError(String err) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), err).build();
    }

//    @Target({ElementType.METHOD})
//    @Retention(RetentionPolicy.RUNTIME)
//    @Inherited
//    public @interface BadRequest  APIResponse{
//        final String code = "100";
//        final String description =  "Bad Request";
//        @Override
//        default String  description() {
//            return description;
//        }
//
//        @Override
//        default String responseCode() {
//            return code;
//        }
//    }
}
