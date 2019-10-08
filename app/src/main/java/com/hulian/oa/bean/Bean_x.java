package com.hulian.oa.bean;

import java.io.Serializable;

/**
 * Created by czy on 2019/7/25 19:37.
 */
public class Bean_x implements Serializable
{
    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getCollectType() {
        return collectType;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    public int getCollectUserId() {
        return collectUserId;
    }

    public void setCollectUserId(int collectUserId) {
        this.collectUserId = collectUserId;
    }

    public int getCollectTypeId() {
        return collectTypeId;
    }

    public void setCollectTypeId(int collectTypeId) {
        this.collectTypeId = collectTypeId;
    }

    public String getCollectTypeTitle() {
        return collectTypeTitle;
    }

    public void setCollectTypeTitle(String collectTypeTitle) {
        this.collectTypeTitle = collectTypeTitle;
    }

    public String getCollectTypeImage() {
        return collectTypeImage;
    }

    public void setCollectTypeImage(String collectTypeImage) {
        this.collectTypeImage = collectTypeImage;
    }

    public String getCollectTypeUserName() {
        return collectTypeUserName;
    }

    public void setCollectTypeUserName(String collectTypeUserName) {
        this.collectTypeUserName = collectTypeUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String collectId;
  private String collectType;
  private int collectUserId;
  private int collectTypeId;
  private String collectTypeTitle;
  private String collectTypeImage;
  private String collectTypeUserName;
  private String createTime;

}
