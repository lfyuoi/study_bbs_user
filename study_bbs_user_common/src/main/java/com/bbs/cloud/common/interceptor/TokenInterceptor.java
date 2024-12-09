package com.bbs.cloud.common.interceptor;


import  com.bbs.cloud.common.contant.RedisContant;
import  com.bbs.cloud.common.local.HostHolder;
import  com.bbs.cloud.common.util.JsonUtils;
import  com.bbs.cloud.common.util.RedisOperator;
import  com.bbs.cloud.common.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 拦截器
 * 获取token中的ticket会话令牌
 * 验证该令牌的有效性
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    final static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ticket = resolveFromAuthorizationHeader(request);
        logger.info("拦截器获取令牌, ticket:{}, request:{}", ticket);

        if(StringUtils.isEmpty(ticket)) {
            logger.info("拦截器获取令牌, 令牌为空, ticket:{}", ticket);
            return false;
        }
        String json = redisOperator.get(RedisContant.BBS_CLOUD_USER_TICKET_KEY + ticket);
        if(StringUtils.isEmpty(json)) {
            logger.info("拦截器获取令牌, 获取用户信息为空, ticket:{}", ticket);
            return false;
        }

        try {
            UserVO userVO = JsonUtils.jsonToPojo(json, UserVO.class);
            if(userVO == null) {
                logger.info("拦截器获取令牌, 用户转换为空, ticket:{}", ticket);
                return false;
            }
            hostHolder.setUser(userVO);
        } catch (Exception e) {
            logger.info("拦截器获取令牌, 用户转换异常, ticket:{}", ticket);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取request中的token
     * @param request
     * @return
     */
    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        //<token>的值就是真实的表达式配置的值
        Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$", Pattern.CASE_INSENSITIVE);

        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return null;
        }
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            //请输入令牌
            throw null;
        }
        return matcher.group("token");//从上面的正则表达式中获取token
    }

    /**
     * 在请求处理之后、视图渲染之前被调用，这里为空实现。
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
