package com.bbs.cloud.essay.message.handler;

import com.bbs.cloud.common.enums.gift.GiftEnum;
import com.bbs.cloud.common.message.essay.EssayMessageDTO;
import com.bbs.cloud.common.message.essay.dto.PlayTourMessage;
import com.bbs.cloud.common.message.essay.enums.EssayMessageTypeEnum;
import com.bbs.cloud.common.util.CommonUtil;
import com.bbs.cloud.common.util.JsonUtils;
import com.bbs.cloud.essay.contant.EssayContant;
import com.bbs.cloud.essay.dto.PlayTourRecordDTO;
import com.bbs.cloud.essay.mapper.PlayTourRecordMapper;
import com.bbs.cloud.essay.message.EssayMessageHandler;
import com.bbs.cloud.user.contant.UserContant;
import com.bbs.cloud.user.dto.BackpackDTO;
import com.bbs.cloud.user.dto.BackpackGiftDTO;
import com.bbs.cloud.user.mapper.BackpackGiftMapper;
import com.bbs.cloud.user.mapper.BackpackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EssayPlayTourMessageHandler implements EssayMessageHandler {

    final static Logger logger = LoggerFactory.getLogger(EssayPlayTourMessageHandler.class);

    @Autowired
    private PlayTourRecordMapper playTourRecordMapper;

    @Autowired
    private BackpackGiftMapper backpackGiftMapper;

    @Autowired
    private BackpackMapper backpackMapper;

    @Override
    public void handler(EssayMessageDTO essayMessageDTO) {
        try {
            logger.info("开始处理打赏礼物产生的消息,message：{}", JsonUtils.objectToJson(essayMessageDTO));
            //扣减DB的数据库礼物列表
            String message = essayMessageDTO.getMessage();
            PlayTourMessage playTourMessage = JsonUtils.jsonToPojo(message, PlayTourMessage.class);
            Integer giftType = playTourMessage.getGiftType();
            BackpackDTO backpackDTO = backpackMapper.queryBackpackDTO(essayMessageDTO.getUserId());
            List<BackpackGiftDTO> backpackGiftDTOS = backpackGiftMapper.queryBackpackGiftDTOList(backpackDTO.getId());
            for (BackpackGiftDTO backpackGiftDTO : backpackGiftDTOS) {
                if (backpackGiftDTO.getGiftType().equals(giftType)) {
                    logger.info("开始处理打赏礼物产生的消息,backpackGiftDTOS：{},giftType,", JsonUtils.objectToJson(backpackGiftDTOS), giftType);
                    if (backpackGiftDTO.getAmount() < UserContant.DEFAULT_CONSUME_GIFT_AMOUT){
                        Integer price = GiftEnum.getGiftsMap().get(giftType).getPrice();
                        logger.info("开始处理打赏礼物产生的消息,礼物数量少于一个礼物,扣减DB的数据库中背包的金币,backpackGiftDTO：{},减少金币price：{}", JsonUtils.objectToJson(backpackGiftDTO),price);
                        backpackDTO.setGold(backpackDTO.getGold() - price);
                        backpackMapper.updateBackpack(backpackDTO);
                    }else {
                        logger.info("开始处理打赏礼物产生的消息,礼物数量大于一个礼物,扣减DB的数据库中背包的礼物,backpackGiftDTO：{}", JsonUtils.objectToJson(backpackGiftDTO));
                        backpackGiftDTO.setAmount(backpackGiftDTO.getAmount() - UserContant.DEFAULT_CONSUME_GIFT_AMOUT);
                        backpackGiftMapper.updateBackpackGift(backpackGiftDTO);
                    }

                }
            }
            //添加打赏记录
            PlayTourRecordDTO playTourRecordDTO = new PlayTourRecordDTO();
            playTourRecordDTO.setId(CommonUtil.createUUID());
            playTourRecordDTO.setSendUserId(playTourMessage.getSendUserId());
            playTourRecordDTO.setEntityType(playTourMessage.getEntityType());
            playTourRecordDTO.setGiftType(playTourMessage.getGiftType());
            playTourRecordDTO.setCreateDate(new Date());
            playTourRecordDTO.setTakeUserId(essayMessageDTO.getEssayId());
            playTourRecordDTO.setEntityId(playTourMessage.getEntityId());
            logger.info("开始处理打赏礼物产生的消息,添加打赏记录,playTourRecordDTO：{}", JsonUtils.objectToJson(playTourRecordDTO));
            playTourRecordMapper.insertPlayTourRecordMapper(playTourRecordDTO);
        } catch (Exception e) {
            logger.error("处理打赏礼物产生的消息，发生异常，message：{}", essayMessageDTO);
            e.printStackTrace();
        }


    }

    @Override
    public String getEssayHandlerType() {
        return EssayMessageTypeEnum.BBS_CLOUD_PLAY_TOUR.getType();
    }
}
