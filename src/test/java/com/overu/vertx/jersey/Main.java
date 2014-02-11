package com.overu.vertx.jersey;

import com.overu.vertx.jersey.bean.Test;
import com.overu.vertx.jersey.bean.User;
import com.overu.vertx.jersey.utils.ClientProvider;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.BoostingQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.factor.FactorBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.facet.Facet;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;

import java.io.IOException;

public class Main {

  public final static String[] names = { "张一", "张二", "张三", "李一", "李二", "李三", "周一", "周二", "周三", "刘一", "刘二", "刘三" };
  public final static String[] pinyins = {
      "zhangyi", "zhanger", "zhangsan", "liyi", "lier", "lisan", "zhouyi", "zhouer", "zhousan", "liuyi", "liuer", "liusan" };

  public static XContentBuilder getMapping() throws IOException {
    XContentBuilder mapping =
        XContentFactory.jsonBuilder().startObject().startObject("user").field("index_analyzer", "opyAnalyzer").startObject("properties")
            .startObject("id").field("type", "long").field("store", "yes").endObject().startObject("name").field("type", "string").field(
                "store", "yes").field("index", "analyzed").endObject().startObject("pinyin").field("type", "string").field("store", "yes")
            .field("index", "analyzed").endObject().startObject("type").field("type", "string").field("store", "yes").endObject()
            .endObject().endObject().endObject();
    return mapping;
  }

  public static String getSetting() throws IOException {
    XContentBuilder mapping =
        XContentFactory.jsonBuilder().startObject().field("number_of_shards", 5).field("number_of_replicas", 0).startObject("analysis")
            .startObject("filter").startObject("opyGram").field("type", "nGram").field("min_gram", 1).field("max_gram", 15).endObject()
            .endObject().startObject("analyzer").startObject("opyAnalyzer").field("type", "custom").field("tokenizer", "standard").field(
                "filter", new String[] { "lowercase", "opyGram" }).endObject().endObject().endObject().endObject();
    return mapping.string();
  }

  public static void main(String[] args) throws IOException {
    ClientProvider provider = ClientProvider.getProvider();

    Settings settings =
        ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").put("client.transport.ping_timeout", "10s").build();
    Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

    // index
    // client.prepareIndex("overu1", "user").setId("14").setSource(provider.convertionJson(new Test())).execute()
    // .actionGet();

    // delete
    // client.prepareDelete("overu1", "user", "14").execute().actionGet();

    // bulk index
    // BulkRequestBuilder indexBulk = client.prepareBulk();
    // for (int i = 0, len = 12; i < len; i++) {
    // String userJson = provider.convertionJson(new User(names[i], pinyins[i]));
    // IndexRequestBuilder bulkCell = client.prepareIndex("overu1", "user").setId(String.valueOf(i)).setSource(userJson);
    // indexBulk.add(bulkCell);
    // }
    // BulkResponse indexBulkResp = indexBulk.execute().actionGet();
    // if (indexBulkResp.hasFailures()) {
    // System.out.println(indexBulkResp.buildFailureMessage());
    // }

    // get
    // GetResponse get = client.prepareGet("overu", "user", "1").execute().actionGet();
    // System.out.println("id:" + get.getId());
    // System.out.println("name:" + ClientProvider.getProvider().convertionObject(get.getSourceAsString(), User.class).getName());

    // delete
    // DeleteResponse delete = client.prepareDelete("overu", "user", "1").execute().actionGet();
    // System.out.println("id:" + delete.getId());
    // System.out.println("source:" + ClientProvider.getProvider().convertionJson(delete.getHeaders()));

    // search
    // BoolQueryBuilder query = QueryBuilders.boolQuery();
    // query.must(QueryBuilders.fieldQuery("name", "xie"));
    // SearchResponse searchResp =
    // client.prepareSearch("overu").setTypes("user").setQuery(query).setFrom(1).setSize(50).execute().actionGet();
    // SearchHits hits = searchResp.getHits();
    // for (SearchHit hit : hits) {
    // System.out.println("score: " + hit.getScore() + " id: " + hit.getId() + " source: " + hit.getSourceAsString());
    // }

    // search facet
    // TermsFacetBuilder facetGroup = FacetBuilders.termsFacet("facetGroup");
    // facetGroup.field("type").size(10);
    // facetGroup.facetFilter(FilterBuilders.matchAllFilter());
    // SearchResponse searchFacet = client.prepareSearch("overu").setTypes("user").addFacet(facetGroup).execute().actionGet();
    // Facets facets = searchFacet.getFacets();
    // TermsFacet facet = (TermsFacet) facets.getFacets().get("facetGroup");
    // for (TermsFacet.Entry entry : facet) {
    // System.out.println("type: " + entry.getTerm() + "  " + entry.getCount());
    // }

    // delete index
    // client.admin().indices().delete(new DeleteIndexRequest("overu")).actionGet();

    // create mapping
    // Builder setting = ImmutableSettings.builder().loadFromSource(getSetting());
    // client.admin().indices().prepareCreate("overu1").setSettings(setting).addMapping("user", getMapping()).execute().actionGet();

    // multi query
    // MultiMatchQueryBuilder query = QueryBuilders.multiMatchQuery("周", "name", "pinyin");
    // SearchResponse multiQuery =
    // client.prepareSearch("overu1", "overu").setTypes("user", "user").setQuery(query).setFrom(0).setSize(15).execute().actionGet();
    // SearchHits hits = multiQuery.getHits();
    // for (SearchHit hit : hits) {
    // System.out
    // .println("score: " + hit.getScore() + " name: " + hit.getSource().get("name") + " pinyin: " + hit.getSource().get("pinyin"));
    // }

    client.close();
  }

}
