package com.bbs.cloud.user.dto;

import java.util.Date;

/**
 * 积分消费记录
 */
public class ScoreConsumeRecordDTO {

    private String id;

    private String userId;

    private String activityId;

    private Integer type;

    private Integer scoreConsume;

    private Integer gold;

    private String luckyBagId;

    private Integer giftType;

    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getScoreConsume() {
        return scoreConsume;
    }

    public void setScoreConsume(Integer scoreConsume) {
        this.scoreConsume = scoreConsume;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public String getLuckyBagId() {
        return luckyBagId;
    }

    public void setLuckyBagId(String luckyBagId) {
        this.luckyBagId = luckyBagId;
    }

    public Integer getGiftType() {
        return giftType;
    }

    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
