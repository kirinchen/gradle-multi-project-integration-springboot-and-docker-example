package net.surfm.account.dto;

import java.util.Date;

import net.surfm.infrastructure.JsonMap;


public class UserObjDto {

	private String uid;
	private int updateNum;
	private Date updateAt;
	private Date createAt;
	private boolean systemed;
	private boolean combined;
	private JsonMap data;
	private double amount;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getUpdateNum() {
		return updateNum;
	}

	public void setUpdateNum(int updateNum) {
		this.updateNum = updateNum;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public boolean isSystemed() {
		return systemed;
	}

	public void setSystemed(boolean systemed) {
		this.systemed = systemed;
	}

	public boolean isCombined() {
		return combined;
	}

	public void setCombined(boolean combined) {
		this.combined = combined;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public JsonMap getData() {
		return data;
	}

	public void setData(JsonMap data) {
		this.data = data;
	}
	
	
	
}
