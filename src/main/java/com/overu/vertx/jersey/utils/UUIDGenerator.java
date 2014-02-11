package com.overu.vertx.jersey.utils;

import java.util.UUID;

public class UUIDGenerator {

  public static String genUUID() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

}
