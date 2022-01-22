package com.inicu.models;

public class ResetPasswordInfoPOJO {

    /*@NotNull
     @NotEmpty
    */String userName;

    /*@NotNull
     @NotEmpty
    */

    /*@NotNull
    @NotEmpty
    */String currentPassword;

     /*@NotNull
    @NotEmpty
    */String newPassword;


     /*@NotNull
    @NotEmpty
    */String confirmNewPassword;

    /*@NotNull
    @NotEmpty
    */String branchname;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }
}
