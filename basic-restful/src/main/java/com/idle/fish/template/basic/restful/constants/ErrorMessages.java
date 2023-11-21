package com.idle.fish.template.basic.restful.constants;

import lombok.experimental.UtilityClass;

/**
 * @author idle fish
 * @since 2023/11/9
 */
@UtilityClass
public class ErrorMessages {

    public static final String REQ_NOT_NULL = "请求参数不能为空";
    public static final String NOT_LOGIN = "您还未登录或者登录已超时，请重新登录";
    public static final String SYSTEM_ERROR = "系统繁忙，请稍后再试";

    public static final String NOT_PERMIT = "您没有权限执行此操作";
}
