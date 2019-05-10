package net.surfm.account.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author kirin
 *
 */
@CheckProtectCode
public class ExLoginFormDto {

	private String uid;
	private String protectCode;

	@NotBlank(message = "uid cannot be blank.")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@NotBlank(message = "protectCode cannot be blank.")
	public String getProtectCode() {
		return protectCode;
	}

	public void setProtectCode(String protectCode) {
		this.protectCode = protectCode;
	}

}
