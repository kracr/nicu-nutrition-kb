package com.inicu.models;

import java.util.List;

import com.inicu.postgres.entities.NotificationEmail;

public class EmailManagementJSON {

	NotificationEmail currentItem;

	List<NotificationEmail> emailList;
	
	List<String> userNameList;
	
	List<String> userEmailList;

	public EmailManagementJSON() {
		this.currentItem = new NotificationEmail();
	}

	public NotificationEmail getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(NotificationEmail currentItem) {
		this.currentItem = currentItem;
	}

	public List<NotificationEmail> getEmailList() {
		return emailList;
	}

	public void setEmailList(List<NotificationEmail> emailList) {
		this.emailList = emailList;
	}

	public List<String> getUserNameList() {
		return userNameList;
	}

	public void setUserNameList(List<String> userNameList) {
		this.userNameList = userNameList;
	}

	public List<String> getUserEmailList() {
		return userEmailList;
	}

	public void setUserEmailList(List<String> userEmailList) {
		this.userEmailList = userEmailList;
	}

}
