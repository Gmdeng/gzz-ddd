package com.gzz.core.validation.validator;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证手机号，空和正确的手机号都能验证通过<br/>
 * 正确的手机号由11位数字组成，第一位为1
 */
@ConstraintComposition(CompositionType.OR)
@Pattern(regexp = "1[1|2|3|4|5|6|7|8|9]\\d{9}")
@Null
@Length(min = 0, max = 0)
@Documented
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface CheckMobileNo {
    String message() default "无效的手机号";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
