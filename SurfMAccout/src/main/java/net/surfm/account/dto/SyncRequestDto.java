package net.surfm.account.dto;

import org.hibernate.validator.constraints.NotBlank;

public class SyncRequestDto {

	private Era era = Era.Mobile;
	private int dataVersion;
	private String uid;

	public int getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(int dataVersion) {
		this.dataVersion = dataVersion;
	}

	@NotBlank(message = "bot code key cannot be blank.")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Era getEra() {
		return era;
	}

	public void setEra(Era era) {
		this.era = era;
	}

}
