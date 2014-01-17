package com.overu.vertx.jersey;

import com.englishtown.vertx.jersey.JerseyModule;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class OJerseyVerticle extends Verticle {

  @Override
  public void start(final Future<Void> startedResult) {

    JsonObject config = container.config();

    String s = "";

    container.deployVerticle(JerseyModule.class.getName(), config, new Handler<AsyncResult<String>>() {

      @Override
      public void handle(AsyncResult<String> result) {

        if (result.succeeded()) {
          startedResult.setResult(null);
          start();
        } else {
          startedResult.setFailure(result.cause());
        }
      }

    });
  }
}
