package com.overu.vertx.jersey;

import com.englishtown.vertx.jersey.inject.VertxJerseyBinder;
import com.overu.vertx.jersey.utils.ClientProvider;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.vertx.java.platform.Container;
import javax.inject.Inject;

public class IntegrationBinder extends AbstractBinder {

  @Inject
  Container container;

  @Override
  protected void configure() {
    install(new VertxJerseyBinder());
    ClientProvider.getProvider().setContainer(container);
  }
}
