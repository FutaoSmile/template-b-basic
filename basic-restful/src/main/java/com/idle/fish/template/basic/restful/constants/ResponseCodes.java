package com.idle.fish.template.basic.restful.constants;

import lombok.experimental.UtilityClass;

/**
 * 响应状态码
 *
 * @author idle fish
 * @since 2023/11/21
 */
@UtilityClass
public class ResponseCodes {
    /**
     * 正常
     */
    public static final int SUCCESS = 0;
    /**
     * 逻辑异常
     */
    public static final int LOGIC_FAIL = -1;
    /**
     * 未登录状态码
     */
    public static final int NOT_LOGIN = 401;
    /**
     * 无权限，禁止访问
     */
    public static final int FORBIDDEN = 403;
    /**
     * 系统异常
     */
    public static final int SYSTEM_ERROR_FAIL = 500;

}
