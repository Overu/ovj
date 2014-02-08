package com.overu.vertx.jersey.bean;

import java.io.Serializable;
import java.util.Random;

public class User implements Serializable {

  private static final long serialVersionUID = 8000420225838383456L;

  private static String[] types = new String[] { "type1", "type2", "type3", "type4", "type5", "type6", "type7" };

  private long id;
  private int subid;
  private String name;
  private String type;

  public User() {
    Random r = new Random();

    setId(r.nextLong());
    setSubid(r.nextInt());
    setName(getSubid() % 2 == 0 ? "ou" : "xie");
    setType(types[Math.abs(getSubid() % 7)]);
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
   * @return the subid
   */
  public int getSubid() {
    return subid;
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
   * @param subid the subid to set
   */
  public void setSubid(int subid) {
    this.subid = subid;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }
}
