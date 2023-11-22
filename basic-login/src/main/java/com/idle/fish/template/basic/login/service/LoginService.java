package com.idle.fish.template.basic.login.service;

import com.idle.fish.tamplate.basic.user.BasicUser;

/**
 * @author idle fish
 * @since 2023/11/21
 */
public interface LoginService {

    /**
     * 通过用户名密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    BasicUser loginByUsernamePassword(String username, String password);
}
