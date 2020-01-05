package com.example.gymapp;

public class Membership {
    private String membershipID;
    private String userID;
    private String type;
    private String startDate;
    private String endDate;
    private String pid;
    private String phone;
    private String address;

    public Membership(String user, String type, String startDate, String endDate, String id, String phone, String address) {
        this.userID = user;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pid = id;
        this.phone = phone;
        this.address = address;
    }

    public String getMembershipID() {
        return membershipID;
    }

    public void setMembershipID(String membershipID) {
        this.membershipID = membershipID;
    }

    public String getUser() {
        return userID;
    }

    public void setUser(String user) {
        this.userID = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getpId() {
        return pid;
    }

    public void setpId(String id) {
        this.pid = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
