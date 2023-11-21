package com.idle.fish.template.basic.util;


import com.idle.fish.template.basic.util.constants.CommonConstant;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.UUID;

/**
 * @author idle fish
 * @since 2023/11/9
 */
@UtilityClass
public class StrUtils {


    public String uuid() {
        return UUID.randomUUID().toString().replace(CommonConstant.MINUS_SIGN, StringUtils.EMPTY);
    }

    public String substring(String string, int targetLength) {
        return Optional.ofNullable(string).filter(str -> str.length() >= targetLength).map(str -> str.substring(0, targetLength)).orElse(StringUtils.EMPTY);
    }
}