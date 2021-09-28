package com.example.otplogin;

public class LoginCredentials {
    private String phoneForLogin;
    private String passwordForLogin;

    public LoginCredentials(String phoneForLogin, String passwordForLogin) {
        this.phoneForLogin = phoneForLogin;
        this.passwordForLogin = passwordForLogin;
    }

    public String getPhoneForLogin() {
        return phoneForLogin;
    }

    public void setPhoneForLogin(String phoneForLogin) {
        this.phoneForLogin = phoneForLogin;
    }

    public String getPasswordForLogin() {
        return passwordForLogin;
    }

    public void setPasswordForLogin(String passwordForLogin) {
        this.passwordForLogin = passwordForLogin;
    }
}
