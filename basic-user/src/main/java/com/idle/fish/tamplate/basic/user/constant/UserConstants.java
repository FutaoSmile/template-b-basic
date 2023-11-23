package com.idle.fish.tamplate.basic.user.constant;

import lombok.experimental.UtilityClass;

/**
 * @author idle fish
 * @since 2023/11/23
 */
@UtilityClass
public class UserConstants {
    /**
     * 当前登录用户信息在dubbo上下文附件中的key
     */
    public static final String DUBBO_ATTACHMENT_USER = "currentThreadUser";
}
