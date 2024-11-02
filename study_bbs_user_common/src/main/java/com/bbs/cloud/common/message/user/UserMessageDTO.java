package com.bbs.cloud.common.message.user;


import  com.bbs.cloud.common.util.CommonUtil;

import java.util.Date;

public class UserMessageDTO {

    private String id;

    private String type;

    private String userId;

    private String message;

    private Date createDate;

    public UserMessageDTO() {
        this.setCreateDate(new Date());
    }

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


    public static UserMessageDTO getUserMessageDTO(String type, String userId, String message) {
        UserMessageDTO userMessageDTO = new UserMessageDTO();
        userMessageDTO.setId(CommonUtil.createUUID());
        userMessageDTO.setUserId(userId);
        userMessageDTO.setType(type);
        userMessageDTO.setMessage(message);
        userMessageDTO.setCreateDate(new Date());
        return userMessageDTO;
    }
}
