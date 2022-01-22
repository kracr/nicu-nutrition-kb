package com.inicu.models;



public class UserLoginInfoPojo {

	/*@NotNull
	@NotEmpty
	*/String username;
	
	/*@NotNull
	@NotEmpty
	*/String password;

	/*@NotNull
	@NotEmpty
	*/String timeZone = "UTC";
	
	/*@NotNull
	@NotEmpty
	*/String schemaName = "inicudbKdahSchema";
	
	/*@NotNull
	@NotEmpty
	*/String branchName;

	/*@NotNull
	@NotEmpty
	*/String branchType;


	public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getTimeZone() {
	return timeZone;
}
public void setTimeZone(String timeZone) {
	this.timeZone = timeZone;
}
public String getSchemaName() {
	return schemaName;
}
public void setSchemaName(String schemaName) {
	this.schemaName = schemaName;
}
public String getBranchName() {
	return branchName;
}
public void setBranchName(String branchName) {
	this.branchName = branchName;
}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
}
