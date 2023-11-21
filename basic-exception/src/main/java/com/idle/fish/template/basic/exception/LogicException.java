package com.idle.fish.template.basic.exception;

import com.idle.fish.template.basic.restful.constants.ResponseCodes;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * 逻辑异常
 *
 * @author idle fish
 * @since 2023/11/9
 */
@Getter
public class LogicException extends RuntimeException {
    private final Integer code;

    private LogicException(String message, int code) {
        super(message);
        this.code = code;
    }

    public static LogicException le(String msg, int code) {
        return new LogicException(msg, code);
    }

    public static LogicException le(String msg) {
        return le(msg, ResponseCodes.LOGIC_FAIL);
    }

    public static void leThrow(String msg) {
        throw le(msg, ResponseCodes.LOGIC_FAIL);
    }

    public static Supplier<LogicException> les(String msg) {
        return () -> LogicException.le(msg);
    }

    public static Supplier<LogicException> les(String msg, int code) {
        return () -> LogicException.le(msg, code);
    }
}
