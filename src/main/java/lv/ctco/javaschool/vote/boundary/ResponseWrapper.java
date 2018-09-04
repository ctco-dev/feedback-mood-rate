package lv.ctco.javaschool.vote.boundary;

import javax.ws.rs.core.Response;

public class ResponseWrapper {

    public Response getMethodNotAllowed() {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    public Response getBadRequest() {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    public Response getCreated() {
        return Response.status(Response.Status.CREATED).build();
    }
}
