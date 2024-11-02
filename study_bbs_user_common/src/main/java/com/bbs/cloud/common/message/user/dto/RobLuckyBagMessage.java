package com.bbs.cloud.common.message.user.dto;

public class RobLuckyBagMessage {

    private Integer giftType;

    private String luckyBagId;

    private String activityId;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Integer getGiftType() {
        return giftType;
    }

    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public String getLuckyBagId() {
        return luckyBagId;
    }

    public void setLuckyBagId(String luckyBagId) {
        this.luckyBagId = luckyBagId;
    }

    public RobLuckyBagMessage(Integer giftType, String luckyBagId, String activityId) {
        this.giftType = giftType;
        this.luckyBagId = luckyBagId;
        this.activityId = activityId;
    }

    public RobLuckyBagMessage() {

    }
}
