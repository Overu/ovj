package com.overu.vertx.jersey;

import com.englishtown.vertx.jersey.inject.VertxJerseyBinder;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class IntegrationBinder extends AbstractBinder {

  @Override
  protected void configure() {

    install(new VertxJerseyBinder());

  }
}
