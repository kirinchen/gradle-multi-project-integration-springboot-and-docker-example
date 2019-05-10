package net.surfm.account.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 
 * @author Kirin
 *
 */
public class LoginResult {
	private String uid;
	private String jSessionId;
	private String username;
	private String xGunUid;
	private Integer steamId;
	private String fbId;
	private LoginType accountType;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
	private Date createAt;

	public String getjSessionId() {
		return jSessionId;
	}

	public void setjSessionId(String jSessionId) {
		this.jSessionId = jSessionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getxGunUid() {
		return xGunUid;
	}

	public void setxGunUid(String xGunUid) {
		this.xGunUid = xGunUid;
	}

	public Integer getSteamId() {
		return steamId;
	}

	public void setSteamId(Integer steamId) {
		this.steamId = steamId;
	}

	public LoginType getAccountType() {
		return accountType;
	}

	public void setAccountType(LoginType accountType) {
		this.accountType = accountType;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
