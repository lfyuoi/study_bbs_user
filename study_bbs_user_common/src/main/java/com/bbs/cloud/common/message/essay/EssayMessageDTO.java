package com.bbs.cloud.common.message.essay;


import  com.bbs.cloud.common.util.CommonUtil;

import java.util.Date;

public class EssayMessageDTO {

    private String id;

    private String type;

    private String userId;

    private String essayId;

    private String message;

    private Date createDate;

    public String getEssayId() {
        return essayId;
    }

    public void setEssayId(String essayId) {
        this.essayId = essayId;
    }

    public EssayMessageDTO() {
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
    public static EssayMessageDTO getEssayMessageDTO(String type, String userId, String essayId,String message) {
        EssayMessageDTO essayMessageDTO = new EssayMessageDTO();
        essayMessageDTO.setId(CommonUtil.createUUID());
        essayMessageDTO.setUserId(userId);
        essayMessageDTO.setType(type);
        essayMessageDTO.setMessage(message);
        essayMessageDTO.setCreateDate(new Date());
        essayMessageDTO.setEssayId(essayId);
        return essayMessageDTO;
    }
}
