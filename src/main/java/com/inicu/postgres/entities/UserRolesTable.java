package com.inicu.postgres.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users_roles")
public class UserRolesTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="userroleid", columnDefinition="bigserial")
	private BigInteger userRoleId;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="roleid")
	private String roleId;
	
	private String branchname;

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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

}
