package api;

import javax.ws.rs.core.Response;

//The class and methods need not be public
class Util {
    static Response badRequest(String err) {
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), err).build();
    }

    static Response notFound() {
        return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
    }
}
