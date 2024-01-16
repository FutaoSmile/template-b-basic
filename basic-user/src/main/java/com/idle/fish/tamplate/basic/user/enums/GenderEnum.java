package com.idle.fish.tamplate.basic.user.enums;

import com.idle.fish.template.basic.util.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别
 *
 * @author fish_temp_author
 * @since fish_temp_since
 */
@Getter
@AllArgsConstructor
public enum GenderEnum implements IEnum<Integer> {
    /**
     * 0=未知
     */
    UN_KNOWN(0, "未知"),
    /**
     * 1=男
     */
    MALE(1, "男"),
    /**
     * 2=女
     */
    FEMALE(2, "女"),
    /**
     * 3=其它
     */
    OTHER(3, "其它");


    private final int gender;
    private final String description;

    @Override
    public Integer logicValue() {
        return gender;
    }
}
