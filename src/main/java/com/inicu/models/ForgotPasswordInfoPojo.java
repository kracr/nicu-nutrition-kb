package com.inicu.models;

public class ForgotPasswordInfoPojo {

//	/*@NotNull
//	@NotEmpty
//	*/String userName;
	
	/*@NotNull
	@NotEmpty
	*/String emailAddress;
	
	/*@NotNull
	@NotEmpty
	*/String branchname;
	
//	public String getUserName() {
//	    return userName;
//    }
//	public void setUsername(String userName) {
//		this.userName = userName;
//	}
	
	public String getEmailAddress() {
	    return emailAddress;
    }
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getBranchname() {
	    return branchname;
    }
	public void setBranchname(String branch) {
		this.branchname = branch;
	}


}
