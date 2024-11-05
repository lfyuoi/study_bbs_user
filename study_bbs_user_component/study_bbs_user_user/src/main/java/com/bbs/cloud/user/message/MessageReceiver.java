package com.bbs.cloud.user.message;

import com.bbs.cloud.common.contant.RabbitContant;
import com.bbs.cloud.common.message.user.UserMessageDTO;
import com.bbs.cloud.common.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class MessageReceiver {

    final static Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    @Autowired
    private List<MessageHandler> messageHandlers;

    @RabbitListener(queues = RabbitContant.USER_QUEUE_NAME)
    public void receiver(String message) {
        logger.info("接收到用户产生的消息：{}", message);
        if (StringUtils.isEmpty(message)) {
            logger.info("接收到用户产生的消息，消息为空:{}", message);
            return;
        }
        UserMessageDTO userMessageDTO;
        try {
            userMessageDTO = JsonUtils.jsonToPojo(message, UserMessageDTO.class);
            if (userMessageDTO == null) {
                logger.info("接收到用户产生的消息，消息转换为空:{}", message);
                return;
            }
            ;
        } catch (Exception e) {
            logger.info("接收到用户产生的消息，消息转换异常:{}", message);
            e.printStackTrace();
            return;
        }
        String messageDTOType = userMessageDTO.getType();

        messageHandlers.stream()
                .filter(item -> item.gerHandlerType().equals(messageDTOType))
                .findFirst().get()
                .handler(userMessageDTO);

    }
}
