package com.inicu.postgres.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity	
@Table(name="users_roles")
public class UserRoles {
	
	@Id
	@Column(name="userroleid", columnDefinition="bigserial")
	private BigInteger userRoleId;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="roleid")
	private String roleId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public BigInteger getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(BigInteger userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
