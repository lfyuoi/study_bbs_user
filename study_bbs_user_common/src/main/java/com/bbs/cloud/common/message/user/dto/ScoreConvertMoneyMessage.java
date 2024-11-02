package com.bbs.cloud.common.message.user.dto;

public class ScoreConvertMoneyMessage {

    private String activityId;

    private Integer consumeScore;

    private Integer gold;

    public Integer getConsumeScore() {
        return consumeScore;
    }

    public void setConsumeScore(Integer consumeScore) {
        this.consumeScore = consumeScore;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
