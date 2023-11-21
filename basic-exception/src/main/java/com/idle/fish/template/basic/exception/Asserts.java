package com.idle.fish.template.basic.exception;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author idle fish
 * @since 2023/11/9
 */
@UtilityClass
public class Asserts {
    public void notNull(Object o, String errorMessage) {
        if (o == null) {
            throw LogicException.le(errorMessage);
        }
    }

    public void notNull(Object o, String errorMessage, int code) {
        if (o == null) {
            throw LogicException.le(errorMessage, code);
        }
    }

    public void notEmpty(Object o, String errorMessage) {
        if (o == null) {
            throw LogicException.le(errorMessage);
        }
        if (o instanceof Map && ((Map<?, ?>) o).isEmpty()) {
            throw LogicException.le(errorMessage);
        }
        if (o instanceof Collection && ((Collection<?>) o).isEmpty()) {
            throw LogicException.le(errorMessage);
        }
        if (o instanceof String && (((String) o).isEmpty() || ((String) o).trim().isEmpty())) {
            throw LogicException.le(errorMessage);
        }
    }

    public void notEmpty(Object o, String errorMessage, int code) {
        if (o == null) {
            throw LogicException.le(errorMessage, code);
        }
        if (o instanceof Map && ((Map<?, ?>) o).isEmpty()) {
            throw LogicException.le(errorMessage, code);
        }
        if (o instanceof Collection && ((Collection<?>) o).isEmpty()) {
            throw LogicException.le(errorMessage, code);
        }
        if (o instanceof String && (((String) o).isEmpty() || ((String) o).trim().isEmpty())) {
            throw LogicException.le(errorMessage, code);
        }
    }

    public void equals(Object o1, Object o2, String message) {
        if (!Objects.equals(o1, o2)) {
            throw LogicException.le(message);
        }
    }

    public void equals(Object o1, Object o2, String message, int code) {
        if (!Objects.equals(o1, o2)) {
            throw LogicException.le(message, code);
        }
    }

    public void notEquals(Object o1, Object o2, String message) {
        if (Objects.equals(o1, o2)) {
            throw LogicException.le(message);
        }
    }

    public void notEquals(Object o1, Object o2, String message, int code) {
        if (Objects.equals(o1, o2)) {
            throw LogicException.le(message, code);
        }
    }

    public void gte(int v1, int v2, String message) {
        if (v1 < v2) {
            throw LogicException.le(message);
        }
    }

    public void gte(long v1, long v2, String message) {
        if (v1 < v2) {
            throw LogicException.le(message);
        }
    }

    public void lte(long v1, long v2, String message) {
        if (v1 > v2) {
            throw LogicException.le(message);
        }
    }

    public void lte(int v1, int v2, String message) {
        if (v1 > v2) {
            throw LogicException.le(message);
        }
    }

    public void truth(boolean condition, String message) {
        if (!condition) {
            throw LogicException.le(message);
        }
    }

    public void truth(boolean condition, String message, int code) {
        if (!condition) {
            throw LogicException.le(message, code);
        }
    }
}
