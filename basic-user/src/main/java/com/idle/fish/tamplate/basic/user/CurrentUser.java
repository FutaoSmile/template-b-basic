package com.idle.fish.tamplate.basic.user;

import lombok.experimental.UtilityClass;

/**
 * 与当前线程绑定的用户
 *
 * @author idle fish
 * @since 2023/11/21
 */
@UtilityClass
public class CurrentUser {
    private static final ThreadLocal<BasicUser> USER_LOGIN_MODEL_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取当前登录的用户信息
     *
     * @return 当前登录用户主键
     */
    public static BasicUser getUser() {
        return USER_LOGIN_MODEL_THREAD_LOCAL.get();
    }

    public static void set(BasicUser currentUser) {
        USER_LOGIN_MODEL_THREAD_LOCAL.set(currentUser);
    }

    public static void clear() {
        USER_LOGIN_MODEL_THREAD_LOCAL.remove();
    }
}
