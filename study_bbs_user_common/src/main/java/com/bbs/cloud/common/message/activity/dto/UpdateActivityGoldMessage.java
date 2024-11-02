package com.bbs.cloud.common.message.activity.dto;

public class UpdateActivityGoldMessage {

    private String activityId;

    private Integer gold;


    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }
}
