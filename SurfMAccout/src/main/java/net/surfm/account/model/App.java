package net.surfm.account.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import net.surfm.account.SDKUtils;
import net.surfm.account.service.CommService;
import net.surfm.constant.JpaConstant;

@SuppressWarnings("serial")
@Entity
@EntityListeners(CommService.class)
@Table(name = "app")
public class App implements Serializable,CreateUpdateDate {

	private String uid;
	private String name;
	private String creatorOid;
	private Date createAt;
	private Date updateAt;

	@Id
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_UID, unique = true)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Transient
	public void setupUid() {
		uid= SDKUtils.getAppUid(name);
	}
	
	
	@Column(nullable = false, unique = true , columnDefinition = JpaConstant.COLUMN_DEFIN_MEDIUM_TEXT)
	@Length(max = 255, min =3 , message = "The length of the contact name cannot larger than 255 or less 8.")
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = true, columnDefinition = JpaConstant.COLUMN_DEFIN_UID)
	public String getCreatorOid() {
		return creatorOid;
	}

	public void setCreatorOid(String creatorOid) {
		this.creatorOid = creatorOid;
	}

	@Override
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_DATE)
	public Date getCreateAt() {
		return createAt;
	}

	@Override
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@Override
	@Column(nullable = true, columnDefinition = JpaConstant.COLUMN_DEFIN_DATE)
	public Date getUpdateAt() {
		return updateAt;
	}

	@Override
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

}
