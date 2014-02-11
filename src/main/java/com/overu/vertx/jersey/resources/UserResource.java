package com.overu.vertx.jersey.resources;

import com.overu.vertx.jersey.model.User;
import com.overu.vertx.jersey.utils.ClientProvider;
import com.overu.vertx.jersey.utils.Constant;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("user")
public class UserResource {

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public User getUser(@PathParam("id") String id) {
    ClientProvider provider = ClientProvider.getProvider();
    User user =
        provider.convertionObject(provider.getClient().prepareGet(Constant.BASEINDEX, Constant.USER_TYPE, id).execute().actionGet()
            .getSourceAsString(), User.class);
    return user;
  }

  @GET
  @Path("all")
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> getUsers(@DefaultValue("0") @QueryParam("start") int start, @DefaultValue("5") @QueryParam("size") int size) {
    ClientProvider provider = ClientProvider.getProvider();
    SearchResponse searchResp =
        provider.getClient().prepareSearch(Constant.BASEINDEX).setTypes(Constant.USER_TYPE).setFrom(start).setSize(size).execute()
            .actionGet();

    List<User> users = new ArrayList<User>();
    for (SearchHit hit : searchResp.getHits()) {
      User u = provider.convertionObject(hit.getSourceAsString(), User.class);
      users.add(u);
    }
    return users;
  }
}
