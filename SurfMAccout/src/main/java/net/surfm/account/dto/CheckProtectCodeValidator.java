package net.surfm.account.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import net.surfm.infrastructure.CommUtils;

public class CheckProtectCodeValidator implements ConstraintValidator<CheckProtectCode, ExLoginFormDto> {

	public static final String TOKEN_KEY = "@S7j33GHcTsk82r";

	@Override
	public void initialize(CheckProtectCode constraintAnnotation) {
	}

	@Override
	public boolean isValid(ExLoginFormDto value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		String plain = value.getUid() + TOKEN_KEY;
		return StringUtils.equals(CommUtils.sha1(plain), value.getProtectCode());

	}

}
