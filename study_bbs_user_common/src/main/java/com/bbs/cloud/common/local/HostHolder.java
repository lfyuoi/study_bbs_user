package com.bbs.cloud.common.local;

import  com.bbs.cloud.common.vo.UserVO;
import org.springframework.stereotype.Component;

/**
 * Created by nowcoder on 2016/7/3.
 */
@Component
public class HostHolder {
    private static ThreadLocal<UserVO> users = new ThreadLocal<UserVO>();

    public UserVO getUser() {
        return users.get();
    }

    public void setUser(UserVO user) {
        users.set(user);
    }

    public void clear() {
        users.remove();;
    }
}
