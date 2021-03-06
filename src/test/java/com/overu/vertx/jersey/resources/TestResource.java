/*
 * The MIT License (MIT) Copyright © 2013 Englishtown <opensource@englishtown.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.overu.vertx.jersey.resources;

import org.elasticsearch.client.Client;
import org.glassfish.jersey.server.ChunkedOutput;
import org.glassfish.jersey.server.JSONP;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Test jersey resource
 */
@Path("test")
public class TestResource {

  @GET
  @Path("chunked")
  @Produces(MediaType.TEXT_PLAIN)
  public void getChunked(@Suspended final AsyncResponse response, @Context final Vertx vertx) {
    vertx.runOnContext(new Handler<Void>() {
      @Override
      public void handle(Void aVoid) {

        final ChunkedOutput<String> chunkedOutput = new ChunkedOutput<>(String.class);

        response.resume(chunkedOutput);

        vertx.runOnContext(new Handler<Void>() {
          @Override
          public void handle(Void aVoid) {
            String s = "aaaaaaaaaa";
            try {
              chunkedOutput.write(s);
              chunkedOutput.write(s);
              chunkedOutput.write(s);
              chunkedOutput.close();
            } catch (IOException e) {
            }
          }
        });
      }
    });

  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getHelloWorld() {
    return "Hello world";
  }

  @GET
  @Path("html")
  @Produces(MediaType.TEXT_HTML)
  public Response getHtml() throws Exception {
    URI uri = this.getClass().getResource("/web/index.html").toURI();
    File file = new File(uri);
    if (!file.exists()) {
      throw new RuntimeException("File web/index.html does not exist");
    }
    return Response.ok(file).build();
  }

  @GET
  @JSONP(queryParam = "cb")
  @Path("json")
  @Produces({ MediaType.APPLICATION_JSON, "application/javascript" })
  public String getJson() {
    return "{'a': 'b'}";
  }

  // @POST
  // @Path("json")
  // @Consumes(MediaType.APPLICATION_JSON)
  // @Produces(MediaType.APPLICATION_JSON)
  // public void postJson(final MyObject obj, @Suspended final AsyncResponse response, @Context Vertx vertx) {
  //
  // vertx.runOnContext(new Handler<Void>() {
  // @Override
  // public void handle(Void event) {
  // if (obj == null) {
  // throw new RuntimeException();
  // }
  // MyObject obj2 = new MyObject();
  // obj2.setName("async response");
  // response.resume(obj2);
  // }
  // });
  // }

  private void writeChunk(ChunkedOutput<String> chunkedOutput) {

  }

}
