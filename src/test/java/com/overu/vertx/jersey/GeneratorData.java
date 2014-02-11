package com.overu.vertx.jersey;

import com.overu.vertx.jersey.model.User;
import com.overu.vertx.jersey.utils.ClientProvider;
import com.overu.vertx.jersey.utils.Constant;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

import java.io.IOException;

public class GeneratorData {

  public XContentBuilder getMapping() throws IOException {
    XContentBuilder mapping =
        XContentFactory.jsonBuilder().startObject().startObject("user").field("index_analyzer", "opyAnalyzer").startObject("properties")
            .startObject("id").field("type", "string").field("store", "yes").endObject().startObject("name").field("type", "string").field(
                "store", "yes").field("index", "analyzed").endObject().startObject("pinyin").field("type", "string").field("store", "yes")
            .field("index", "analyzed").endObject().startObject("age").field("type", "integer").field("store", "yes").endObject()
            .endObject().endObject().endObject();
    return mapping;
  }

  public String getSetting() throws IOException {
    XContentBuilder mapping =
        XContentFactory.jsonBuilder().startObject().field("number_of_shards", 5).field("number_of_replicas", 0).startObject("analysis")
            .startObject("filter").startObject("opyGram").field("type", "nGram").field("min_gram", 1).field("max_gram", 15).endObject()
            .endObject().startObject("analyzer").startObject("opyAnalyzer").field("type", "custom").field("tokenizer", "standard").field(
                "filter", new String[] { "lowercase", "opyGram" }).endObject().endObject().endObject().endObject();
    return mapping.string();
  }

  @Test
  public void testGenData() throws IOException {
    ClientProvider provider = ClientProvider.getProvider();

    Settings settings =
        ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").put("client.transport.ping_timeout", "10s").build();
    Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

    // Builder mappingSettings = ImmutableSettings.settingsBuilder().loadFromSource(getSetting());
    // client.admin().indices().prepareCreate(Constant.BASEINDEX).setSettings(mappingSettings).addMapping(Constant.USER_TYPE, getMapping())
    // .execute().actionGet();

    BulkRequestBuilder indexBulk = client.prepareBulk();
    for (int i = 0, len = 12; i < len; i++) {
      User u = new User(Main.names[i], Main.pinyins[i], 0);
      indexBulk.add(client.prepareIndex(Constant.BASEINDEX, Constant.USER_TYPE, String.valueOf(i)).setSource(provider.convertionJson(u)));
    }

    BulkResponse bulkResp = indexBulk.execute().actionGet();
    if (bulkResp.hasFailures()) {
      client.close();
      return;
    }

    client.close();
  }
}
