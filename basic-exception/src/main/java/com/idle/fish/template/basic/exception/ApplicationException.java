package com.idle.fish.template.basic.exception;

import java.util.function.Supplier;

/**
 * @author idle fish
 * @since 2023/11/21
 */
public class ApplicationException extends RuntimeException {
    private ApplicationException() {
    }

    private ApplicationException(String message) {
        super(message);
    }

    private ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ApplicationException ae(String message) {
        return new ApplicationException(message);
    }

    public static ApplicationException ae(String message, Throwable e) {
        return new ApplicationException(message, e);
    }

    public static Supplier<ApplicationException> aes(String msg) {
        return () -> ApplicationException.ae(msg);
    }

    public static Supplier<ApplicationException> aes(String msg, Throwable e) {
        return () -> ApplicationException.ae(msg, e);
    }
}
