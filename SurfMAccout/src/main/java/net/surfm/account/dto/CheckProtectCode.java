package net.surfm.account.dto;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckProtectCodeValidator.class)
@Documented
public @interface CheckProtectCode {

	  String message() default "Protect Code Error";

	  Class<?>[] groups() default {};

	  Class<? extends Payload>[] payload() default {};
	
}
