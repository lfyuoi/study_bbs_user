package com.bbs.cloud.essay.dto;

/**
 * 话题
 */
public class EssayTopicDTO {

    /**
     * 话题ID
     */
    private String id;

    /**
     * 文章ID
     */
    private String essayId;

    /**
     * 话题规则
     */
    private String rule;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEssayId() {
        return essayId;
    }

    public void setEssayId(String essayId) {
        this.essayId = essayId;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
