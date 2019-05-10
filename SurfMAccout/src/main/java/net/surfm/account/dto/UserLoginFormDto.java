package net.surfm.account.dto;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import net.surfm.infrastructure.DebotCodeGot;

/**
 * 
 * @author kirin
 *
 */
public class UserLoginFormDto implements DebotCodeGot {

	private String email;
	private String password;
	private String recaptchaResponse;
	private LoginType bindType = LoginType.NONE;
	private String bindUid = StringUtils.EMPTY;
	private String skipCode;

	@NotBlank(message = "Email cannot be blank.")
	@Email(message = "Invalid email format.")
	@Length(max = 255, message = "The length of the contact email cannot larger than 255.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotBlank(message = "Password cannot be blank.")
	@Length(min = 6, message = "The length of password must greater than 6.")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginType getBindType() {
		return bindType;
	}

	public void setBindType(LoginType bindType) {
		this.bindType = bindType;
	}

	public String getBindUid() {
		return bindUid;
	}

	public void setBindUid(String bindUid) {
		this.bindUid = bindUid;
	}

	@Override
	public String getRecaptchaResponse() {
		return recaptchaResponse;
	}

	public void setRecaptchaResponse(String recaptchaResponse) {
		this.recaptchaResponse = recaptchaResponse;
	}

	public String getSkipCode() {
		return skipCode;
	}

	public void setSkipCode(String skipCode) {
		this.skipCode = skipCode;
	}
	
	

}
