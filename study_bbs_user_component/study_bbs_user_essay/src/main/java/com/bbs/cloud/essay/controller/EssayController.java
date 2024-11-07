package com.bbs.cloud.essay.controller;

import com.bbs.cloud.common.result.HttpResult;
import com.bbs.cloud.essay.param.CommentParam;
import com.bbs.cloud.essay.param.EssayPublishParam;
import com.bbs.cloud.essay.param.LikeParam;
import com.bbs.cloud.essay.param.PlayTourParam;
import com.bbs.cloud.essay.service.EssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/essay")
public class EssayController {

    @Autowired
    private EssayService essayService;
    /**
     * 文章发布接口
     *
     * @return
     */
    @PostMapping("/publish")
    public HttpResult publish(@RequestBody EssayPublishParam param) {
        return essayService.publish(param);
    }

    /**
     * 评论接口
     *
     * @return
     */
    @PostMapping("/comment/add")
    public HttpResult addComment(@RequestBody CommentParam param) {
        return essayService.addComment(param);
    }

    /**
     * 点赞接口
     *
     * @return
     */
    @PostMapping("/liked")
    public HttpResult liked(@RequestBody LikeParam param) {
        return essayService.liked(param);
    }

    /**
     * 取消点赞接口
     *
     * @return
     */
    @PostMapping("/unliked")
    public HttpResult unliked(@RequestBody LikeParam param) {
        return essayService.unliked(param);
    }

    /**
     * 打赏接口
     *
     * @return
     */
    @PostMapping("/tour/play")
    public HttpResult playTour(@RequestBody PlayTourParam param) {
        return essayService.playTour(param);
    }
}
