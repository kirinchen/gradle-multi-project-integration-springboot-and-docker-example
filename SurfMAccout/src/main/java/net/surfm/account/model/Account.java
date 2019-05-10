package net.surfm.account.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.surfm.account.dto.LoginType;
import net.surfm.constant.JpaConstant;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author kirin
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "account", indexes = { @Index(name = "idx_email", columnList = "email") })
public class Account extends org.springframework.security.core.userdetails.User {

	private String uid;
	private Integer id;
	private int steamId;
	private String fbId;
	private String email;
	private String phone;
	private String username;
	private String password;
	private String _password;
	private String nickName;
	private String avatar;
	private LoginType type;

	private boolean enabled;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private Set<Authority> authorities = new HashSet<Authority>();

	private Date createAt;

	public Account() {
		super("_USERNAME", "_PASSWORD", new ArrayList<Authority>());
	}

	@Id
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_ID)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Email(message = "Invalid email format.")
	@Column(nullable = false, unique = true, columnDefinition = JpaConstant.COLUMN_DEFIN_MEDIUM_TEXT)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>(authorities);
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "account_authority", joinColumns = {
			@JoinColumn(name = "accountId", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "authorityId", referencedColumnName = "id") })
	public Set<Authority> getAuthoritiesSet() {
		return authorities;
	}

	public void setAuthoritiesSet(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_MEDIUM_TEXT)
	public String getPassword() {
		return password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	@Override
	@Column(nullable = false, unique = true, columnDefinition = JpaConstant.COLUMN_DEFIN_MEDIUM_TEXT)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_BOOLEAN)
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_BOOLEAN)
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_BOOLEAN)
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_BOOLEAN)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(nullable = true, columnDefinition = JpaConstant.COLUMN_DEFIN_MEDIUM_TEXT)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(nullable = true, columnDefinition = JpaConstant.COLUMN_DEFIN_MEDIUM_TEXT)
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_SMALL_TEXT)
	public LoginType getType() {
		return type;
	}

	public void setType(LoginType type) {
		this.type = type;
	}

	@Column(nullable = true, columnDefinition = JpaConstant.COLUMN_DEFIN_INT)
	public int getSteamId() {
		return steamId;
	}

	public void setSteamId(int steamId) {
		this.steamId = steamId;
	}

	@Column(nullable = true, columnDefinition = JpaConstant.COLUMN_DEFIN_MEDIUM_TEXT)
	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_DATE)
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_SMALL_TEXT, unique = true)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(nullable = true, columnDefinition = JpaConstant.COLUMN_DEFIN_SMALL_TEXT)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(nullable = false, columnDefinition = JpaConstant.COLUMN_DEFIN_MEDIUM_TEXT)
	public String get_password() {
		return _password;
	}

	public void set_password(String _password) {
		this._password = _password;
	}

	
	
}
