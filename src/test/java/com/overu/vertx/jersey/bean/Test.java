package com.overu.vertx.jersey.bean;

import java.util.Random;

public class Test {

  private long mId;

  public Test() {
    Random r = new Random();
    setmId(r.nextLong());
  }

  /**
   * @return the mId
   */
  public long getmId() {
    return mId;
  }

  /**
   * @param mId the mId to set
   */
  public void setmId(long mId) {
    this.mId = mId;
  }

}
