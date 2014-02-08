package com.overu.vertx.jersey;

import com.overu.vertx.jersey.bean.User;
import com.overu.vertx.jersey.utils.ClientProvider;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.BoostingQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
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

public class Main {

  public static void main(String[] args) {
    ClientProvider provider = ClientProvider.getProvider();

    Settings settings =
        ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").put("client.transport.ping_timeout", "10s").build();
    Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

    // index
    // client.prepareIndex("overu", "user").setId("1").setSource(ClientProvider.getProvider().convertionJson(new User())).execute()
    // .actionGet();

    // bulk index
    // BulkRequestBuilder indexBulk = client.prepareBulk();
    // for (int i = 0, len = 500; i < len; i++) {
    // String userJson = provider.convertionJson(new User());
    // IndexRequestBuilder bulkCell = client.prepareIndex("overu", "user").setId(String.valueOf(i)).setSource(userJson);
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

    client.close();
  }
}
