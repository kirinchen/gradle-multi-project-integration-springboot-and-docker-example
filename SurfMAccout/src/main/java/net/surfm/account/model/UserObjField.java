package net.surfm.account.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.surfm.account.service.CommService;
import net.surfm.constant.JpaConstant;

import net.surfm.infrastructure.JsonMap.MergePV;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_obj_field")
@EntityListeners(CommService.class)
public class UserObjField implements Serializable,CreateUpdateDate {

	private String uid;
	private String name;
	private Date updateAt;
	private Date createAt;
	private String value;
	private UserObj obj;
	private ObjField fieldInfo;

	@Id
	@Column(nullable = false, unique = true, columnDefinition = JpaConstant.COLUMN_DEFIN_UID)	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(nullable = false,  columnDefinition = JpaConstant.COLUMN_DEFIN_LARGE_TEXT)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_DATE)
	public Date getUpdateAt() {
		return updateAt;
	}

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

	@Column(nullable = true,  columnDefinition = JpaConstant.COLUMN_DEFIN_XLARGE_TEXT)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ManyToOne
	@JoinColumn(name = "obj_oid", referencedColumnName = "uid")	
	public UserObj getObj() {
		return obj;
	}

	public void setObj(UserObj obj) {
		this.obj = obj;
	}

	@ManyToOne
	@JoinColumn(name = "field_oid", referencedColumnName = "uid")	
	public ObjField getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(ObjField fieldInfo) {
		this.fieldInfo = fieldInfo;
	}


}
