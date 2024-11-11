package com.bbs.cloud.essay.message.handler;

import com.bbs.cloud.common.message.essay.EssayMessageDTO;
import com.bbs.cloud.common.message.essay.enums.EssayMessageTypeEnum;
import com.bbs.cloud.common.util.JsonUtils;
import com.bbs.cloud.essay.contant.EssayContant;
import com.bbs.cloud.essay.message.EssayMessageHandler;
import com.bbs.cloud.user.dto.ScoreCardDTO;
import com.bbs.cloud.user.mapper.ScoreCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EssayPublishMessageHandler implements EssayMessageHandler {

    final static Logger logger = LoggerFactory.getLogger(EssayPublishMessageHandler.class);

    @Autowired
    private ScoreCardMapper scoreCardMapper;

    @Override
    public void handler(EssayMessageDTO essayMessageDTO) {

        logger.info("开始处理文章发布消息，消息内容：{}", JsonUtils.objectToJson(essayMessageDTO));
        try {
            String userId = essayMessageDTO.getUserId();
            logger.info("开始添加用户积分，userId:{}", userId);
            ScoreCardDTO scoreCardDTO = scoreCardMapper.queryScoreCardDTO(userId);
            logger.info("开始添加用户积分，userId:{},用户积分卡：{}", userId, JsonUtils.objectToJson(scoreCardDTO));
            Integer score = scoreCardDTO.getScore() + EssayContant.DEFAULT_ESSAY_PUSH_SCORE;
            scoreCardDTO.setScore(scoreCardDTO.getScore() + EssayContant.DEFAULT_ESSAY_PUSH_SCORE);
            scoreCardMapper.updateScoreCard(scoreCardDTO);
            logger.info("用户积分添加完成，发布文章成功积分加100，userId:{},score:{}", userId, score);
        }catch (Exception e){
            logger.error("文章发布消息处理失败，发生异常,消息内容：{}", JsonUtils.objectToJson(essayMessageDTO), e);
            e.printStackTrace();
        }

    }

    @Override
    public String getEssayHandlerType() {
        return EssayMessageTypeEnum.BBS_CLOUD_ESSAY_PUBLISH.getType();
    }
}
