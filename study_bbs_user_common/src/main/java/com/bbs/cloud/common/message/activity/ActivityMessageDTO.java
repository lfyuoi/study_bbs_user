package com.bbs.cloud.common.message.activity;


import  com.bbs.cloud.common.util.CommonUtil;

import java.util.Date;

public class ActivityMessageDTO {

    private String id;

    private String type;

    private String userId;

    private String message;

    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public static ActivityMessageDTO getActivityMessageDTO(String type, String userId, String message) {
        ActivityMessageDTO activityMessageDTO = new ActivityMessageDTO();
        activityMessageDTO.setId(CommonUtil.createUUID());
        activityMessageDTO.setUserId(userId);
        activityMessageDTO.setType(type);
        activityMessageDTO.setMessage(message);
        activityMessageDTO.setCreateDate(new Date());
        return activityMessageDTO;
    }
}
