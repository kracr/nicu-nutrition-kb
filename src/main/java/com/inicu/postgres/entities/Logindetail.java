package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the logindetails database table.
 * 
 */
@Entity
@Table(name="logindetails")
@NamedQuery(name="Logindetail.findAll", query="SELECT l FROM Logindetail l")
public class Logindetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long loginid;

	private String ipaddress;

	private Timestamp logintime;

	private Timestamp logouttime;

	private String userid;

	private String branchname;

	@Column(name ="logout" , columnDefinition = "bool")
	private boolean logout;

	public Logindetail() {
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public Long getLoginid() {
		return this.loginid;
	}

	public void setLoginid(Long loginid) {
		this.loginid = loginid;
	}

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Timestamp getLogintime() {
		return this.logintime;
	}

	public void setLogintime(Timestamp logintime) {
		this.logintime = logintime;
	}

	public Timestamp getLogouttime() {
		return this.logouttime;
	}

	public void setLogouttime(Timestamp logouttime) {
		this.logouttime = logouttime;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public boolean isLogout() {
		return logout;
	}

	public void setLogout(boolean logout) {
		this.logout = logout;
	}
}