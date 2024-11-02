package com.bbs.cloud.common.message.activity.dto;

public class UpdateLuckyBagMessage {

    private String activityId;

    private String luckyBagId;

    public String getLuckyBagId() {
        return luckyBagId;
    }

    public void setLuckyBagId(String luckyBagId) {
        this.luckyBagId = luckyBagId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public UpdateLuckyBagMessage(String activityId, String luckyBagId) {
        this.activityId = activityId;
        this.luckyBagId = luckyBagId;
    }

    public UpdateLuckyBagMessage() {

    }
}
