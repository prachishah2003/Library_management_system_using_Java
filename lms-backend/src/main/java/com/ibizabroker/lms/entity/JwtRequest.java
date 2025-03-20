package com.ibizabroker.lms.entity;

public class JwtRequest {

    private String username;
    private String password;

    public JwtRequest(String testUser, String password) {
        this.username=testUser;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserPassword(String userPassword) {
        this.password = userPassword;
    }
}
