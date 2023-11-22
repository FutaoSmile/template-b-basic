package com.idle.fish.template.basic.login.enums;

import com.idle.fish.template.basic.util.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author idle fish
 * @since 2023/11/22
 */
@Getter
@AllArgsConstructor
public enum UserRoleEnum implements IEnum<Integer> {
    /**
     * 0=管理员
     */
    ADMIN(0, "超管"),
    /**
     * 1=普通用户
     */
    NORMAL_USER(1, "普通用户");

    private final int role;
    private final String desc;

    @Override
    public Integer logicValue() {
        return role;
    }
}
