package com.idle.fish.template.basic.util.enums;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Optional;

/**
 * 枚举工具类
 *
 * @author idle fish
 * @since 2023/11/9
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumUtils {

    /**
     * 根据枚举类和值查找枚举对象
     *
     * @param tClass
     * @param value
     * @param <T>
     * @return
     */
    public static <T extends IEnum<?>> T byValue(Class<T> tClass, Object value) {
        T[] enumConstants = tClass.getEnumConstants();
        for (T enumConstant : enumConstants) {
            if (Objects.equals(enumConstant.logicValue(), value)) {
                return enumConstant;
            }
        }
        return null;
    }

    public static <T extends IEnum<?>> T byValueWithDefault(Class<T> tClass, Object value, T defaultValue) {
        T[] enumConstants = tClass.getEnumConstants();
        for (T enumConstant : enumConstants) {
            if (Objects.equals(enumConstant.logicValue(), value)) {
                return enumConstant;
            }
        }
        return defaultValue;
    }

    public static <T extends IEnum<?>> Optional<T> byValueOptional(Class<T> tClass, Object value) {
        T[] enumConstants = tClass.getEnumConstants();
        for (T enumConstant : enumConstants) {
            if (Objects.equals(enumConstant.logicValue(), value)) {
                return Optional.of(enumConstant);
            }
        }
        return Optional.empty();
    }

}
