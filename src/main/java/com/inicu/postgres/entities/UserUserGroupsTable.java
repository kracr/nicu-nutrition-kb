package com.inicu.postgres.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users_usergroups")
public class UserUserGroupsTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id",columnDefinition="bigserial")
	private BigInteger id ;
	
	@Column(name="userid")
	private String userName;
	
	@Column(name="usergroupid")
	private String uesrGroupId;
	
	private String branchname;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUesrGroupId() {
		return uesrGroupId;
	}

	public void setUesrGroupId(String uesrGroupId) {
		this.uesrGroupId = uesrGroupId;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

}
