package com.idle.fish.template.basic.login.controller;

import com.idle.fish.tamplate.basic.user.BasicUser;
import com.idle.fish.template.basic.exception.Asserts;
import com.idle.fish.template.basic.login.service.LoginService;
import com.idle.fish.template.basic.login.controller.req.UsernamePasswordLoginReq;
import com.idle.fish.template.basic.login.constant.LoginConstant;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录接口
 *
 * @author idle fish
 * @since 2023/11/21
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private LoginService loginService;

    /**
     * 用户名密码登录
     *
     * @param usernamePasswordLoginReq
     * @param request
     */
    @PostMapping("/username-pwd")
    public void login(@RequestBody @Validated UsernamePasswordLoginReq usernamePasswordLoginReq, HttpServletRequest request) {
        BasicUser basicUser = loginService.loginByUsernamePassword(usernamePasswordLoginReq.getUsername(), usernamePasswordLoginReq.getPassword());
        Asserts.notNull(basicUser, "用户名或密码错误");
        // 获取session会话
        HttpSession session = request.getSession(true);
        // 将用户信息保存到session会话中
        session.setAttribute(LoginConstant.SESSION_ATTR, basicUser);
        // 设置session会话过期时间，秒
        session.setMaxInactiveInterval(5);
    }

}
