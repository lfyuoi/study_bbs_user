package com.bbs.cloud.essay.message;

import com.bbs.cloud.common.message.essay.EssayMessageDTO;

public interface EssayMessageHandler {

    public void handler(EssayMessageDTO essayMessageDTO);

    public String getEssayHandlerType();
}
