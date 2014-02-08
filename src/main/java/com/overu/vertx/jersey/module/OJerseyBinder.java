package com.overu.vertx.jersey.module;

import com.englishtown.vertx.jersey.inject.VertxJerseyBinder;
import com.overu.vertx.jersey.utils.ClientProvider;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

import javax.inject.Inject;
import javax.inject.Singleton;

public class OJerseyBinder extends AbstractBinder {

  // class ESClientFactory implements Factory<Client> {
  //
  // private Client client;
  //
  // @Inject
  // public ESClientFactory(Container container) {
  // container.logger().warn(container);
  // JsonObject config = container.config().getObject("elasticsearch").getObject("client").getObject("transport");
  // client =
  // new TransportClient().addTransportAddress(new InetSocketTransportAddress(config.getString("host", "localhost"), config
  // .getInteger("port", 9300)));
  // }
  //
  // @Override
  // public void dispose(Client arg0) {
  //
  // }
  //
  // @Override
  // public Client provide() {
  // return client;
  // }
  //
  // }

  @Inject
  Container container;

  @Override
  protected void configure() {
    install(new VertxJerseyBinder());

    ClientProvider.getProvider().setContainer(container);
    // bind(ESClientFactory.class).to(new TypeLiteral<Client>() {
    // }).in(Singleton.class);
  }
}
