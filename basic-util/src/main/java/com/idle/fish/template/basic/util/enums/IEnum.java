package com.idle.fish.template.basic.util.enums;

/**
 * 枚举类都需要实现的接口
 *
 * @author idle fish
 * @since 2023/11/9
 */
public interface IEnum<R> {
    /**
     * 业务值字段
     *
     * @return
     */
    R logicValue();
}
