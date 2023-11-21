package com.idle.fish.template.basic.login;

import com.idle.fish.tamplate.basic.user.BasicUser;
import com.idle.fish.tamplate.basic.user.CurrentUser;
import com.idle.fish.template.basic.exception.Asserts;
import com.idle.fish.template.basic.exception.LogicException;
import com.idle.fish.template.basic.restful.constants.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 用户登录状态拦截器
 *
 * @author idle fish
 * @since 2023/11/21
 */
@Slf4j
@Component
public class UserAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("request path:{}", request.getRequestURI());
        CurrentUser.clear();
        Object sessionUser = Optional.ofNullable(request.getSession(false))
                .map(session -> session.getAttribute(LoginConstant.SESSION_ATTR))
                .orElseThrow(() -> LogicException.le(ErrorMessages.NOT_LOGIN));
        Asserts.notNull(sessionUser, ErrorMessages.NOT_LOGIN);
        CurrentUser.set((BasicUser) sessionUser);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        CurrentUser.clear();
    }
}