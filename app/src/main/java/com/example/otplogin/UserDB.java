package com.example.otplogin;

import android.widget.RadioButton;

public class UserDB {

    //private String phoneNumber;
    private String password;
    LoginCredentials loginCredentials;
    private String uname;
    private int age;
    private String genderU;
    private String phoneNumber;
    private String trustedContact;

    public UserDB(){
    }


    public UserDB( String name, int age) {
        //this.phoneNumber = phoneNumber;
        this.uname = name;
        this.age = age;

    }

    public UserDB( String name, int age, String gender) {
        //this.phoneNumber = phoneNumber;
        this.uname = name;
        this.age = age;
        this.genderU = gender;
    }

    public UserDB( String name, int age, String gender, String phone) {
        //this.phoneNumber = phoneNumber;
        this.uname = name;
        this.age = age;
        this.genderU = gender;
        this.phoneNumber = phone;
    }

    public UserDB( String name, int age, String gender, String phone, String trustedContact) {
        //this.phoneNumber = phoneNumber;
        this.uname = name;
        this.age = age;
        this.genderU = gender;
        this.phoneNumber = phone;
        this.trustedContact =trustedContact;
    }

    public UserDB( String name, int age, String gender, String phone, String password,LoginCredentials lc) {
        //this.phoneNumber = phoneNumber;
        this.uname = name;
        this.age = age;
        this.genderU = gender;
        this.phoneNumber = phone;
        this.password = password;
        this.loginCredentials = new LoginCredentials(lc.getPhoneForLogin(),lc.getPasswordForLogin());
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGenderU() {
        return genderU;
    }

    public void setGenderU(String genderU) {
        this.genderU = genderU;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String p) {
        this.phoneNumber = p;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getTrustedContact() {
        return trustedContact;
    }

    public void setTrustedContact(String trustedContact) {
        this.trustedContact = trustedContact;
    }
}
