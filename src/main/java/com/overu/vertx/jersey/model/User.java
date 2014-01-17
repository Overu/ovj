package com.overu.vertx.jersey.model;

public class User {

  private String name;

  private int age;

  public User() {
  }

  public User(String name, int age) {
    this.setName(name);
    this.setAge(age);
  }

  public int getAge() {
    return age;
  }

  public String getName() {
    return name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setName(String name) {
    this.name = name;
  }

}
