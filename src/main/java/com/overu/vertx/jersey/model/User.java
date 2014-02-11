package com.overu.vertx.jersey.model;

import com.overu.vertx.jersey.utils.UUIDGenerator;

public class User {

  private String id;
  private String name;
  private String pinyin;
  private int age;

  public User() {
    this(null, null, 0);
  }

  public User(String name, String pinyin, int age) {
    this.setId(UUIDGenerator.genUUID());
    this.setName(name);
    this.setPinyin(pinyin);
    this.setAge(age);
  }

  public int getAge() {
    return age;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPinyin() {
    return pinyin;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPinyin(String pinyin) {
    this.pinyin = pinyin;
  }

}
