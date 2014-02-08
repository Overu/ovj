package com.overu.vertx.jersey;

import com.englishtown.vertx.jersey.JerseyModule;
import com.overu.vertx.jersey.utils.ClientProvider;

import static org.vertx.testtools.VertxAssert.assertEquals;
import static org.vertx.testtools.VertxAssert.fail;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import static org.vertx.testtools.VertxAssert.testComplete;

@RunWith(CPJavaClassRunner.class)
public class OJerseyVerticleTest extends TestVerticle {

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

  // @Test
  public void test_getChunked() throws Exception {

    HttpClient client = createHttpClient();

    HttpClientRequest request = client.get("/rest/test/chunked", new Handler<HttpClientResponse>() {
      @Override
      public void handle(HttpClientResponse response) {
        verifyResponse(response, 200, MediaType.TEXT_PLAIN, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
      }
    });

    request.end();
  }

  @Test
  public void test_getJson() throws Exception {

    HttpClient client = createHttpClient();

    HttpClientRequest request = client.get("/rest/test/json", new Handler<HttpClientResponse>() {
      @Override
      public void handle(HttpClientResponse response) {
        esTest();
        verifyResponse(response, 200, MediaType.APPLICATION_JSON, "{'a': 'b'}");
      }
    });

    request.end();
  }

  private HttpClient createHttpClient() {
    HttpClient client = vertx.createHttpClient();

    client.setHost("localhost").setPort(8080).setConnectTimeout(1000).exceptionHandler(new Handler<Throwable>() {
      @Override
      public void handle(Throwable event) {
        fail();
      }
    });

    return client;
  }

  private void esTest() {
    Settings settings =
        ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").put("client.transport.sniff", true).put(
            "client.transport.ping_timeout", "10s").build();
    Client client = ClientProvider.getProvider().getClient(settings);

    // index
    IndexResponse actionGet = client.prepareIndex("twitter", "tweet").setId("1").setSource("{'a':'b'}").execute().actionGet();

    ClientProvider.getProvider().close();
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

  private void verifyResponse(HttpClientResponse response, int status, String contentType, final String expected) {

    assertEquals(status, response.statusCode());
    assertEquals(contentType, response.headers().get(HttpHeaders.CONTENT_TYPE));

    if (expected != null) {
      response.bodyHandler(new Handler<Buffer>() {
        @Override
        public void handle(Buffer body) {
          String result = body.toString("UTF-8");
          assertEquals(expected, result);
          testComplete();
        }
      });
    } else {
      testComplete();
    }
  }

}
