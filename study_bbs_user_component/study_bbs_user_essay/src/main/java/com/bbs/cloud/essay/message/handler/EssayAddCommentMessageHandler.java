package com.bbs.cloud.essay.message.handler;

import com.bbs.cloud.common.message.essay.EssayMessageDTO;
import com.bbs.cloud.common.message.essay.enums.EssayMessageTypeEnum;
import com.bbs.cloud.common.util.JsonUtils;
import com.bbs.cloud.essay.contant.EssayContant;
import com.bbs.cloud.essay.dto.EssayDTO;
import com.bbs.cloud.essay.mapper.EssayMapper;
import com.bbs.cloud.essay.message.EssayMessageHandler;
import com.bbs.cloud.user.dto.ScoreCardDTO;
import com.bbs.cloud.user.mapper.ScoreCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EssayAddCommentMessageHandler implements EssayMessageHandler {

    final static Logger logger = LoggerFactory.getLogger(EssayAddCommentMessageHandler.class);

    @Autowired
    private EssayMapper essayMapper;

    @Autowired
    private ScoreCardMapper scoreCardMapper;

    @Override
    public void handler(EssayMessageDTO essayMessageDTO) {
        logger.info("开始处理文章评论消息,消息内容:{}", JsonUtils.objectToJson(essayMessageDTO));

        try {
            // 1.添加文章的评论数量
            String essayId = essayMessageDTO.getEssayId();
            EssayDTO essayDTO = essayMapper.queryEssayDTO(essayId);
            essayDTO.setCommentCount(essayDTO.getCommentCount() + EssayContant.DEFAULT_COMMENT_COUNT_INCR);
            logger.info("文章评论数量+1,文章id:{}",essayId);
            essayMapper.updateEssayDTO(essayDTO);

            //2.添加用户积分
            String userId = essayMessageDTO.getUserId();
            logger.info("开始添加用户积分，userId:{}", userId);
            ScoreCardDTO scoreCardDTO = scoreCardMapper.queryScoreCardDTO(userId);
            logger.info("开始添加用户积分，userId:{},用户积分卡：{}", userId, JsonUtils.objectToJson(scoreCardDTO));
            Integer score = scoreCardDTO.getScore() + EssayContant.DEFAULT_ESSAY_COMMENT_ADD_SCORE;
            scoreCardDTO.setScore(scoreCardDTO.getScore() + EssayContant.DEFAULT_ESSAY_PUSH_SCORE);
            scoreCardMapper.updateScoreCard(scoreCardDTO);
            logger.info("用户积分添加完成，评论文章成功积分加10，userId:{},score:{}", userId, score);

            //TODO 3.通知被评论的人?这个需要增加个人消息功能，外加一个个人消息记录表吧？
        }catch (Exception e){
            logger.error("处理文章评论消息失败,发生异常,消息内容:{}", JsonUtils.objectToJson(essayMessageDTO));
            e.printStackTrace();
        }


    }

    @Override
    public String getEssayHandlerType() {
        return EssayMessageTypeEnum.BBS_CLOUD_ESSAY_COMMENT.getType();
    }
}
