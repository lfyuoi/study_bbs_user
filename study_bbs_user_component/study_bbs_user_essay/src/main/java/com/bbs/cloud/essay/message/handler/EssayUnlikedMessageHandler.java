package com.bbs.cloud.essay.message.handler;

import com.bbs.cloud.common.enums.essay.EssayStatusEnum;
import com.bbs.cloud.common.message.essay.EssayMessageDTO;
import com.bbs.cloud.common.message.essay.dto.EssayLikedMessage;
import com.bbs.cloud.common.message.essay.enums.EssayMessageTypeEnum;
import com.bbs.cloud.common.util.JsonUtils;
import com.bbs.cloud.essay.contant.EssayContant;
import com.bbs.cloud.essay.dto.EssayDTO;
import com.bbs.cloud.essay.dto.LikedRecordDTO;
import com.bbs.cloud.essay.mapper.EssayMapper;
import com.bbs.cloud.essay.mapper.LikedRecordMapper;
import com.bbs.cloud.essay.message.EssayMessageHandler;
import com.bbs.cloud.user.dto.ScoreCardDTO;
import com.bbs.cloud.user.mapper.ScoreCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EssayUnlikedMessageHandler implements EssayMessageHandler {

    final static Logger logger = LoggerFactory.getLogger(EssayUnlikedMessageHandler.class);

    @Autowired
    private LikedRecordMapper likedRecordMapper;

    @Autowired
    private EssayMapper essayMapper;

    @Autowired
    private ScoreCardMapper scoreCardMapper;

    @Override
    public void handler(EssayMessageDTO essayMessageDTO) {
        try {
            logger.info("开始处理取消点赞产生的消息,message：{}", JsonUtils.objectToJson(essayMessageDTO));
            String message = essayMessageDTO.getMessage();
            EssayLikedMessage essayLikedMessage = JsonUtils.jsonToPojo(message, EssayLikedMessage.class);
            //1.被点赞的人积分增加
            String essayId = essayMessageDTO.getEssayId();
            EssayDTO essayDTO = essayMapper.queryEssayDTO(essayId);
            String likerId = essayDTO.getUserId();
            ScoreCardDTO scoreCardDTO = scoreCardMapper.queryScoreCardDTO(likerId);
            Integer score = scoreCardDTO.getScore();
            scoreCardDTO.setScore(score - EssayContant.DEFAULT_ESSAY_LIKED_ADD_SCORE);
            logger.info("开始处理取消点赞产生的消息,被点赞的人积分减少,message：{},被点赞的人likerId:{}，原有积分score：{}",JsonUtils.objectToJson(essayMessageDTO),likerId,score);
            scoreCardMapper.updateScoreCard(scoreCardDTO);

            //2.减少点赞记录-更新状态为删除
            LikedRecordDTO likedRecordDTO = new LikedRecordDTO();
            likedRecordDTO.setEssayId(essayId);
            likedRecordDTO.setStatus(EssayStatusEnum.DEL.getStatus());
            likedRecordDTO.setUserId(essayLikedMessage.getUserId());
            likedRecordDTO.setUpdateDate(new Date());
            logger.info("开始处理取消点赞产生的消息,减少点赞记录,message：{}",JsonUtils.objectToJson(likedRecordDTO));
            likedRecordMapper.updateLikedRecordDTO(likedRecordDTO);
            //3.减少文章点赞数量
            Integer likeCount = essayDTO.getLikeCount();
            essayDTO.setLikeCount( likeCount - EssayContant.DEFAULT_LIKED_COUNT_INCR);
            logger.info("开始处理点赞产生的消息,减少文章点赞数量,message：{},;原有文章点赞数量likeCount",JsonUtils.objectToJson(essayDTO),likeCount);
            essayMapper.insertEssayDTO(essayDTO);
        }catch (Exception e){
            logger.info("处理点赞产生的消息,发生异常,message：{}",JsonUtils.objectToJson(essayMessageDTO));
            e.printStackTrace();
        }
    }

    @Override
    public String getEssayHandlerType() {
        return EssayMessageTypeEnum.BBS_CLOUD_ESSAY_UNLIKED.getType();
    }
}
