package com.overu.vertx.jersey;

import com.englishtown.vertx.jersey.JerseyModule;

import static org.vertx.testtools.VertxAssert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static org.vertx.testtools.VertxAssert.testComplete;

@RunWith(CPJavaClassRunner.class)
public class OJerseyVerticleTest extends TestVerticle {

  @Test
  public void getJson() {
    testComplete();
  }

  @Override
  public void start(final Future<Void> startedResult) {
    JsonObject config = loadConfig();

    container.deployVerticle(JerseyModule.class.getName(), config, new Handler<AsyncResult<String>>() {

      @Override
      public void handle(AsyncResult<String> result) {
        if (result.succeeded()) {
          startedResult.setResult(null);
          OJerseyVerticleTest.super.start();
        } else {
          startedResult.setFailure(result.cause());
        }
      }
    });

  }

  private JsonObject loadConfig() {

    try (InputStream stream = this.getClass().getResourceAsStream("/config.json")) {
      StringBuilder sb = new StringBuilder();
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

      String line = reader.readLine();
      while (line != null) {
        sb.append(line).append('\n');
        line = reader.readLine();
      }

      return new JsonObject(sb.toString());

    } catch (IOException e) {
      e.printStackTrace();
      fail();
      return new JsonObject();
    }

  }

}
