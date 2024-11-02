package com.bbs.cloud.common.message.user.dto;

public class ScoreConvertLuckyBagMessage {

    private Integer giftType;

    private String luckyBagId;

    private String activityId;

    private Integer consumeScore;

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

    public Integer getConsumeScore() {
        return consumeScore;
    }

    public void setConsumeScore(Integer consumeScore) {
        this.consumeScore = consumeScore;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
