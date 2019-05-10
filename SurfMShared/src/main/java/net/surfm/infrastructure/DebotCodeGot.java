package net.surfm.infrastructure;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public interface DebotCodeGot{
	
	public static final String SKIP_CODE = "NMKX9uwe89ud89234rhyf6dMNiejf9d9nasbdrdfnasmGDTQW765E763baXCD34BbYMMA";
	
	public String getRecaptchaResponse();
	public String getSkipCode();
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	default boolean skipble() {
		return StringUtils.equals(SKIP_CODE, getSkipCode());
	}
}