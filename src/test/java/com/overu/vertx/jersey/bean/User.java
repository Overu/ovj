package com.overu.vertx.jersey.bean;

import java.io.Serializable;
import java.util.Random;

public class User implements Serializable {

  private static final long serialVersionUID = 8000420225838383456L;

  private static String[] types = new String[] { "type1", "type2", "type3", "type4", "type5", "type6", "type7" };

  private long id;
  private String name;
  private String pinyin;
  private String type;

  public User() {
    this("谢逗逗", "xiedoudou");
  }

  public User(String name, String pinyin) {
    Random r = new Random();
    setId(r.nextLong());
    setType(types[Math.abs((int) getId() % 7)]);
    setName(name);
    setPinyin(pinyin);
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the pinyin
   */
  public String getPinyin() {
    return pinyin;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param id the id to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @param pinyin the pinyin to set
   */
  public void setPinyin(String pinyin) {
    this.pinyin = pinyin;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }
}
