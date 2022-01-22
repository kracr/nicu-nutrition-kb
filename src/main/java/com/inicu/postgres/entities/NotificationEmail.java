package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the notification_email database table.
 * 
 */
@Entity
@Table(name = "notification_email")
@NamedQuery(name = "NotificationEmail.findAll", query = "SELECT n FROM NotificationEmail n")
public class NotificationEmail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long notification_email_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String username;

	private String user_email;

	@Column(columnDefinition = "bool")
	private Boolean isactive;

	@Column(name = "device_exception",columnDefinition = "bool")
	private Boolean deviceException;

	@Column(columnDefinition = "bool")
	private Boolean email_type;
	
	private String branchname;

	@Column(columnDefinition = "bool")
	private Boolean is_adoption;


	public NotificationEmail() {
		super();
		this.isactive = true;
		this.email_type = true;
	}

	public Boolean getIs_adoption() {
		return is_adoption;
	}

	public void setIs_adoption(Boolean is_adoption) {
		this.is_adoption = is_adoption;
	}

	public Long getNotification_email_id() {
		return notification_email_id;
	}

	public void setNotification_email_id(Long notification_email_id) {
		this.notification_email_id = notification_email_id;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public Boolean getEmail_type() {
		return email_type;
	}

	public void setEmail_type(Boolean email_type) {
		this.email_type = email_type;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public Boolean getDeviceException() {
		return deviceException;
	}

	public void setDeviceException(Boolean deviceException) {
		this.deviceException = deviceException;
	}

	@Override
	public String toString() {
		return "NotificationEmail [notification_email_id=" + notification_email_id + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", username=" + username + ", user_email=" + user_email
				+ ", isactive=" + isactive + ", email_type=" + email_type + ", branchname=" + branchname + "]";
	}

}
