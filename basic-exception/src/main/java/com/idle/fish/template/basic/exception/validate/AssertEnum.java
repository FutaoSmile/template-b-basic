package com.idle.fish.template.basic.exception.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * @author idle fish
 * @since 2023/11/9
 */
@Documented
@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AssertEnumValidator.class})
public @interface AssertEnum {
    Class<? extends Enum> enumClazz();

    String getValueMethodName() default "logicValue";

    String message() default "超出取值范围";

    // 以下两行为固定模板
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
