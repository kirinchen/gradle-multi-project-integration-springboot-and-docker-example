package net.surfm.account.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.surfm.account.dto.FieldBehavior;
import net.surfm.account.dto.FieldType;
import net.surfm.account.service.CommService;
import net.surfm.constant.JpaConstant;
import net.surfm.infrastructure.JsonMap;
import net.surfm.jpatool.Array2SetConverter;

@SuppressWarnings("serial")
@Entity
@Table(name = "obj_field")
@EntityListeners(CommService.class)
public class ObjField implements Serializable,CreateUpdateDate  {
	
	

	private String uid;
	private String name;
	private FieldType type;
	private FieldBehavior behavior;
	private Set<String> valueEnums;
	private boolean numDesc;
	private Obj obj;
	private Date updateAt;
	private Date createAt;
	
	
	

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


	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_SMALL_TEXT)
	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_SMALL_TEXT)	
	public FieldBehavior getBehavior() {
		return behavior;
	}

	public void setBehavior(FieldBehavior behavior) {
		this.behavior = behavior;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "obj_oid")
	public Obj getObj() {
		return obj;
	}

	public void setObj(Obj obj) {
		this.obj = obj;
	}

	@Convert(converter = Array2SetConverter.class)
	public Set<String> getValueEnums() {
		return valueEnums;
	}

	public void setValueEnums(Set<String> valueEnums) {
		this.valueEnums = valueEnums;
	}

	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_BOOLEAN)
	public boolean isNumDesc() {
		return numDesc;
	}

	public void setNumDesc(boolean numDesc) {
		this.numDesc = numDesc;
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
	


}
