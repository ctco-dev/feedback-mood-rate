package lv.ctco.javaschool.vote.boundary;

import javax.ws.rs.core.Response;

public class ResponseWrapper {

    public Response getMethodNotAllowedResponse() {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    public Response getBadRequestResponse() {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    public Response getCreatedResponse() {
        return Response.status(Response.Status.CREATED).build();
    }
}
