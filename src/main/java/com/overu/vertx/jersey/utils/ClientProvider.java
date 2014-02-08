package com.overu.vertx.jersey.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.elasticsearch.action.admin.cluster.node.info.NodeInfo;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoRequest;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.facet.terms.TermsFacet.Entry;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

import java.io.IOException;
import java.util.Map;

public class ClientProvider {

  private Container container;
  private Client client;

  private ObjectMapper mapper = new ObjectMapper();

  private volatile static ClientProvider PROVIDER;

  public static ClientProvider getProvider() {
    if (PROVIDER == null) {
      PROVIDER = new ClientProvider();
    }
    return PROVIDER;
  }

  private ClientProvider() {
  }

  public void close() {
    if (client == null) {
      return;
    }
    checkContainer();
    client.close();
    client = null;
  }

  public String convertionJson(Object o) {
    try {
      return mapper.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("o not convert to json");
    }
  }

  public <T> T convertionObject(String json, Class<T> clazz) {
    try {
      return mapper.readValue(json, clazz);
    } catch (Exception e) {
      throw new IllegalArgumentException("json not convert to " + clazz.getName());
    }
  }

  public Client getClient() {
    return getClient(null);
  }

  public Client getClient(Settings settings) {
    if (client != null) {
      return client;
    }
    checkContainer();
    client = settings == null ? new TransportClient() : new TransportClient(settings);
    JsonObject esConfig = container.config().getObject("elasticsearch");
    if (esConfig == null) {
      esConfig = new JsonObject();
      esConfig.putString("host", "localhost").putNumber("port", new Integer(9300));
    } else {
      esConfig = esConfig.getObject("client").getObject("transport");
    }
    ((TransportClient) client).addTransportAddress(new InetSocketTransportAddress(esConfig.getString("host", "localhost"), esConfig
        .getInteger("port", 9300)));
    return client;
  }

  public boolean isConnection() {
    if (client == null) {
      return false;
    }
    try {
      NodesInfoResponse resp = client.admin().cluster().nodesInfo(new NodesInfoRequest().timeout("30")).actionGet();
      Map<String, NodeInfo> nodesMap = resp.getNodesMap();
      for (Map.Entry<String, NodeInfo> entry : nodesMap.entrySet()) {
        System.out.println(entry.getKey() + ":" + entry.getValue().getServiceAttributes());
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public void setContainer(Container container) {
    this.container = container;
  }

  private void checkContainer() {
    if (container == null) {
      throw new IllegalArgumentException("container is null");
    }
  }

}
