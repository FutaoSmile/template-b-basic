package com.idle.fish.template.basic.login.annotations;

import com.idle.fish.template.basic.login.enums.UserRoleEnum;

import java.lang.annotation.*;

/**
 * @author idle fish
 * @since 2023/11/22
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LoginRequire {
    /**
     * 要求的角色，默认是普通用户
     *
     * @return 角色集合
     */
    UserRoleEnum[] requireRoles() default {UserRoleEnum.NORMAL_USER};

}
