package com.bbs.cloud.user.message.handler;

import com.bbs.cloud.common.enums.gift.GiftEnum;
import com.bbs.cloud.common.message.user.UserMessageDTO;
import com.bbs.cloud.common.message.user.dto.RobLuckyBagMessage;
import com.bbs.cloud.common.message.user.dto.RobRedPacketMessage;
import com.bbs.cloud.common.message.user.enums.UserMessageTypeEnum;
import com.bbs.cloud.common.util.CommonUtil;
import com.bbs.cloud.common.util.JsonUtils;
import com.bbs.cloud.user.contant.UserContant;
import com.bbs.cloud.user.dto.*;
import com.bbs.cloud.user.mapper.*;
import com.bbs.cloud.user.message.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RobRedPacketMessageHandler implements MessageHandler {

    final static Logger logger = LoggerFactory.getLogger(RobRedPacketMessageHandler.class);

    @Autowired
    private BackpackMapper backpackMapper;

    @Autowired
    private RedPacketRecordMapper redPacketRecordMapper;

    @Autowired
    private UserLogRecordMapper userLogRecordMapper;


    @Override
    public void handler(UserMessageDTO userMessageDTO) {
        logger.info("用户抢红包产生的消息,message:{}", JsonUtils.objectToJson(userMessageDTO));
        try {
            String userId = userMessageDTO.getUserId();
            String message = userMessageDTO.getMessage();
            RobRedPacketMessage robRedPacketMessage = JsonUtils.jsonToPojo(message, RobRedPacketMessage.class);
            Integer gold = robRedPacketMessage.getGold();


            //第一步：更新用户背包的中的金币
            BackpackDTO backpackDTO = backpackMapper.queryBackpackDTO(userId);
            backpackDTO.setGold(backpackDTO.getGold() + gold);
            backpackMapper.updateBackpack(backpackDTO);

            //第二步：添加用户领取福袋记录
            RedPacketRecordDTO redPacketRecordDTO = new RedPacketRecordDTO();
            redPacketRecordDTO.setId(CommonUtil.createUUID());
            redPacketRecordDTO.setCreateDate(new Date());
            redPacketRecordDTO.setUserId(userId);
            redPacketRecordDTO.setGold(gold);
            redPacketRecordDTO.setActivityId(redPacketRecordDTO.getActivityId());
            redPacketRecordDTO.setRedPacketId(redPacketRecordDTO.getRedPacketId());
            redPacketRecordMapper.insertRedPacketRecord(redPacketRecordDTO);

            //第三步：添加用户操作日志
            UserLogRecordDTO userLogRecordDTO = new UserLogRecordDTO(
                    userId,
                    "恭喜用户抢到红包获得金币 ：" + GiftEnum.getGiftsMap().get(gold).getDesc());
            userLogRecordMapper.insertUserLogRecordDTO(userLogRecordDTO);

        } catch (Exception e) {
            logger.error("用户抢红包产生的消息,发生异常,message:{}", JsonUtils.objectToJson(userMessageDTO), e);
            e.printStackTrace();
        }
    }

    @Override
    public String gerHandlerType() {
        return UserMessageTypeEnum.BBS_CLOUD_USER_ROB_RED_PACKET.getType();
    }
}
