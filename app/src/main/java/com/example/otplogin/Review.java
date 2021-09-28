package com.example.otplogin;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class Review {

    private String uname;
    private String location;
    private int age,time;
    private String genderU,category;
    private Date reviewDate;


    public Review(String uname, String genderU) {
        this.uname = uname;
        this.genderU = genderU;
    }

    public Review(String uname, int age, String genderU) {
        this.uname = uname;
        this.age = age;
        this.genderU = genderU;
    }

    public Review(int age, String genderU,int time, String category, Date date,String location) {
        this.age = age;
        this.genderU = genderU;
        this.category = category;
        this.reviewDate = date;
        this.time = time;
        this.location = location;

    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getGenderU() {
        return genderU;
    }

    public void setGenderU(String genderU) {
        this.genderU = genderU;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }



}
