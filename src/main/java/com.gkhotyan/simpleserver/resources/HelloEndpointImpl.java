package com.gkhotyan.simpleserver.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/query")
public class HelloEndpointImpl implements HelloEndpoint {

  private static Logger logger = LogManager.getLogger();

  @GET
  @Path("/hello")
  public Response get(@QueryParam("name") String name) {
    logger.debug("Hello endpoint. Param: {}", name);
    return Response.status(Response.Status.OK).entity("Hello, "+name).build();
  }
}
