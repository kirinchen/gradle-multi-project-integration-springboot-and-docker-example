package net.surfm.account.dto;

import java.util.Date;

public class AppDto {

	private String uid;
	private String name;
	private String creatorOid;
	private Date createAt;
	private Date updateAt;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatorOid() {
		return creatorOid;
	}

	public void setCreatorOid(String creatorOid) {
		this.creatorOid = creatorOid;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

}
