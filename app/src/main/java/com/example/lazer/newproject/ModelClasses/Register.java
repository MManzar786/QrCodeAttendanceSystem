package com.example.lazer.newproject.ModelClasses;

public class Register{
    String name;
    String regNo;
    String email;
    String password;
    int totalLectures;

    public int getTotalLectures() {
        return totalLectures;
    }

    public void setTotalLectures(int totalLectures) {
        this.totalLectures = totalLectures;
    }

    public Register(String name, String regNo, String email, String password, int totalLectures) {

        this.name = name;
        this.regNo = regNo;
        this.email = email;
        this.password = password;
        this.totalLectures = totalLectures;
    }

    public Register(String regNo, String password) {
        this.regNo = regNo;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
