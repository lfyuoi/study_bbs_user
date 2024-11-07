package com.bbs.cloud.essay.param;

import java.util.List;

/**
 * 文章实体类
 */
public class EssayPublishParam {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    private List<String> topics;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
