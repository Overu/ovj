package com.overu.vertx.jersey.module;

import com.englishtown.vertx.jersey.inject.VertxJerseyBinder;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class OJerseyBinder extends AbstractBinder {

  @Override
  protected void configure() {
    install(new VertxJerseyBinder());
  }

}
