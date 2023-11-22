package com.idle.fish.template.basic.login.controller.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author idle fish
 * @since 2023/11/21
 */
@Getter
@Setter
public class UsernamePasswordLoginReq {
    /**
     * 用户名
     */
    @NotBlank(message = "请输入用户名")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "请输入密码")
    private String password;
}
