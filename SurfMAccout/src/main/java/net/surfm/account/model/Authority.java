package net.surfm.account.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author kirin
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority {

	public enum Role {
		ADMIN, NORMAL, LIMIT;
	}

	private Role role;
	private Long id;
	private String name;

	@Id
	@Column(nullable = false, columnDefinition = "int(11) unsigned")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false, unique = true)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	@Transient
	public String getAuthority() {
		return role.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
