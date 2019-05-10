package net.surfm.account.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.surfm.account.service.CommService;
import net.surfm.constant.JpaConstant;
import net.surfm.infrastructure.JsonMap;
import net.surfm.jpatool.JsonMapConverter;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_obj")
@EntityListeners(CommService.class)
public class UserObj implements Serializable, CreateUpdateDate {

	private String uid;
	private Obj obj;
	private int updateNum;
	private Date updateAt;
	private Date createAt;
	private boolean systemed;
	private boolean combined;
	private JsonMap data;
	private double amount;
	private Account owner;
	private Set<UserObjField> fields;

	@Id
	@Column(nullable = false, unique = true, columnDefinition = JpaConstant.COLUMN_DEFIN_UID)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@OneToOne
	@JoinColumn(name = "obj_oid", referencedColumnName = "uid")	
	public Obj getObj() {
		return obj;
	}

	public void setObj(Obj obj) {
		this.obj = obj;
	}

	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_INT)
	public int getUpdateNum() {
		return updateNum;
	}

	public void setUpdateNum(int updateNum) {
		this.updateNum = updateNum;
	}

	
	@Override
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_DATE)
	public Date getUpdateAt() {
		return updateAt;
	}

	@Override
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
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

	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_BOOLEAN)
	public boolean isSystemed() {
		return systemed;
	}

	public void setSystemed(boolean systemed) {
		this.systemed = systemed;
	}

	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_BOOLEAN)
	public boolean isCombined() {
		return combined;
	}

	public void setCombined(boolean combined) {
		this.combined = combined;
	}


	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_DOUBLE)
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@ManyToOne
	@JoinColumn(name = "account_oid", referencedColumnName = "id")	
	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "obj", cascade = CascadeType.ALL)
	public Set<UserObjField> getFields() {
		return fields;
	}

	public void setFields(Set<UserObjField> fields) {
		this.fields = fields;
	}
	
	@Column(columnDefinition = JpaConstant.COLUMN_DEFIN_XLARGE_TEXT)
	@Convert(converter = JsonMapConverter.class)
	public JsonMap getData() {
		return data;
	}

	public void setData(JsonMap data) {
		this.data = data;
	}	
	
	

}
