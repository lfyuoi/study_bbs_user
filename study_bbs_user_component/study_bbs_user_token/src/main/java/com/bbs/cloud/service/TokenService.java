package com.bbs.cloud.service;


import com.bbs.cloud.common.contant.RedisContant;
import com.bbs.cloud.common.enums.user.LoginTicketStatusEnum;
import com.bbs.cloud.common.local.HostHolder;
import com.bbs.cloud.common.result.HttpResult;
import com.bbs.cloud.common.util.CommonUtil;
import com.bbs.cloud.common.util.JsonUtils;
import com.bbs.cloud.common.util.RedisOperator;
import com.bbs.cloud.common.vo.UserVO;
import com.bbs.cloud.dto.LoginTicketDTO;
import com.bbs.cloud.dto.UserDTO;
import com.bbs.cloud.exception.TokenException;
import com.bbs.cloud.mapper.LoginTicketMapper;
import com.bbs.cloud.mapper.UserMapper;
import com.bbs.cloud.param.LoginParam;
import com.bbs.cloud.param.RegisterParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class TokenService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private HostHolder hostHolder;

    final static Logger logger = LoggerFactory.getLogger(TokenService.class);

    /**
     * 注册
     * @param param
     * @return
     */
    public HttpResult register(RegisterParam param) {
        logger.info("用户开始注册,请求参数:{}", JsonUtils.objectToJson(param));

        String username = param.getUsername();
        if (StringUtils.isEmpty(username)) {
            logger.info("用户开始注册,用户名不能为空,请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(TokenException.USERNAME_IS_NOT_NULL);
        }

        String password = param.getPassword();
        if (StringUtils.isEmpty(password)) {
            logger.info("用户开始注册,密码不能为空,请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(TokenException.PASSWORD_IS_NOT_NULL);
        }

        UserDTO userDTO = userMapper.queryUserByUsername(username);
        if (userDTO != null) {
            logger.info("用户开始注册,用户名已存在,请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(TokenException.USERNAME_IS_EXIST);
        }

        userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setId(CommonUtil.createUUID());
        userDTO.setSalt(CommonUtil.createUUID().substring(0, 5));
        userDTO.setPassword(password + userDTO.getSalt());
        userMapper.insertUser(userDTO);

        LoginTicketDTO loginTicketDTO = new LoginTicketDTO();
        loginTicketDTO.setId(CommonUtil.createUUID());
        loginTicketDTO.setUserId(userDTO.getId());

        String ticket = CommonUtil.createUUID();
        loginTicketDTO.setTicket(ticket);

        loginTicketDTO.setStatus(LoginTicketStatusEnum.VALID.getStatus());
        Date date = new Date();
        long expired = date.getTime() + 24 * 60 * 60 *1000l;
        loginTicketDTO.setExpired(new Date(expired));
        loginTicketMapper.insertTicket(loginTicketDTO);

        /**
         * TODO 异步创建用户的背包，背包礼物列表，积分卡
         */
        UserVO userVO = new UserVO();
        userVO.setId(userDTO.getId());
        userVO.setUsername(userDTO.getUsername());
        redisOperator.set(RedisContant.BBS_CLOUD_USER_TICKET_KEY + ticket,JsonUtils.objectToJson(userVO));

        return new HttpResult(ticket);
    }

    /**
     * 登录
     * @param param
     * @return
     */
    public HttpResult login(LoginParam param) {
        logger.info("用户开始登录,请求参数:{}", JsonUtils.objectToJson(param));

        String username = param.getUsername();
        if (StringUtils.isEmpty(username)) {
            logger.info("用户开始登录,用户名不能为空,请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(TokenException.USERNAME_IS_NOT_NULL);
        }

        String password = param.getPassword();
        if (StringUtils.isEmpty(password)) {
            logger.info("用户开始登录,密码不能为空,请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(TokenException.PASSWORD_IS_NOT_NULL);
        }
        UserDTO userDTO = userMapper.queryUserByUsername(username);
        if (userDTO == null) {
            logger.info("用户开始登录,用户不存在,请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(TokenException.USERNAME_IS_NOT_EXIST);
        }
        if (!userDTO.getPassword().equals(password + userDTO.getSalt())) {
            logger.info("用户开始登录,密码错误,请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(TokenException.PASSWORD_NOT_TRUE);
        }
        LoginTicketDTO loginTicketDTO = loginTicketMapper.getTicketDTOByUserId(userDTO.getId());
        loginTicketDTO.setStatus(LoginTicketStatusEnum.VALID.getStatus());
        Date date = new Date();
        long expired = date.getTime() + 24 * 60 * 60 *1000l;
        loginTicketDTO.setExpired(new Date(expired));
        loginTicketMapper.updateTicket(loginTicketDTO);

        UserVO userVO = new UserVO();
        userVO.setId(userDTO.getId());
        userVO.setUsername(userDTO.getUsername());
        redisOperator.set(RedisContant.BBS_CLOUD_USER_TICKET_KEY + loginTicketDTO.getTicket(),JsonUtils.objectToJson(userVO));

        return new HttpResult(loginTicketDTO.getTicket());
    }

    /**
     * 注销
     * @return
     */
    public HttpResult logout() {
        UserVO userVO = hostHolder.getUser();
        LoginTicketDTO loginTicketDTO = loginTicketMapper.getTicketDTOByUserId(userVO.getId());
        loginTicketDTO.setStatus(LoginTicketStatusEnum.INVALID.getStatus());
        loginTicketMapper.updateTicket(loginTicketDTO);

        redisOperator.del(RedisContant.BBS_CLOUD_USER_TICKET_KEY + loginTicketDTO.getTicket());

        return HttpResult.ok();
    }
}
