package com.example.android.yallasa7elassignment.data;

public class User {

    private String userName;
    private String password;

    public User( String userName, String password, String name, String mobileNumber ) {
        this.userName = userName;
        this.password = password;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
