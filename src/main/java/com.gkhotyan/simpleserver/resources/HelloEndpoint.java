package com.gkhotyan.simpleserver.resources;

import javax.ws.rs.core.Response;

public interface HelloEndpoint {
  public Response get(String name);
}
