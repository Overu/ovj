package com.overu.vertx.jersey.resources;

import com.overu.vertx.jersey.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("user")
public class UserResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public User getUser() {
    return new User("zhang", 11);
  }

}
