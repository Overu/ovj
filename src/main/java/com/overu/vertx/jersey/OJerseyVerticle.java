package com.overu.vertx.jersey;

import com.englishtown.vertx.jersey.JerseyModule;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;

public class OJerseyVerticle extends BusModBase {

  @Override
  public void start(final Future<Void> startedResult) {

    JsonObject jerseyConfig = container.config();
    container.deployVerticle(JerseyModule.class.getName(), jerseyConfig, new Handler<AsyncResult<String>>() {
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
