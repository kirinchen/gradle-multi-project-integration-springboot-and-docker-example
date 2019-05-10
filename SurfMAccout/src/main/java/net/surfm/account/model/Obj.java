package net.surfm.account.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import net.surfm.account.SDKUtils;
import net.surfm.account.service.CommService;
import net.surfm.constant.JpaConstant;
import net.surfm.infrastructure.JsonMap;
import net.surfm.jpatool.JsonMapConverter;

@SuppressWarnings("serial")
@Entity
@Table(name = "obj")
@EntityListeners(CommService.class)
public class Obj implements Serializable , CreateUpdateDate {
	private String uid;
	private String objHash;
	private String name;
	private JsonMap data;
	private int dataVersion;
	private Date updateAt;
	private Date createAt;
	private boolean systemed;
	private boolean combined;
	private App app;
	private List<ObjField> fields;


	@Id
	@Column(nullable = false, unique = true, columnDefinition = JpaConstant.COLUMN_DEFIN_UID)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_LARGE_TEXT)
	public String getObjHash() {
		return objHash;
	}

	public void setObjHash(String objHash) {
		this.objHash = objHash;
	}

	@Column(columnDefinition = JpaConstant.COLUMN_DEFIN_XLARGE_TEXT)
	@Convert(converter = JsonMapConverter.class)
	public JsonMap getData() {
		return data;
	}

	public void setData(JsonMap data) {
		this.data = data;
	}

	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_INT)
	public int getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(int dataVersion) {
		this.dataVersion = dataVersion;
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

	@ManyToOne
	@JoinColumn(name = "app_oid", referencedColumnName = "uid")
	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "obj", cascade = CascadeType.ALL)
	public List<ObjField> getFields() {
		return fields;
	}

	public void setFields(List<ObjField> fields) {
		this.fields = fields;
	}
	
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_LARGE_TEXT)	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public void setupUid() {
		String uid = systemed ? SDKUtils.getSysObjUid(name) : SDKUtils.getObjUid(getApp().getUid(), name);
		setUid(uid);
	}

	
	
}
