package com.example.gymapp;

public class User {

    private String user_id;
    private String user_email;
    public String user_name;
    public String user_birthday;
    public String user_weight;
    public String user_height;
    public String user_gender;
    public boolean member = false;

    public User(String email,String name,String weight,String height,String birthday,String gender)
    {

        this.user_email=email;
        this.user_name = name;
        this.user_weight = weight;
        this.user_height = height;
        this.user_birthday = birthday;
        this.user_gender = gender;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_weight() {
        return user_weight;
    }

    public void setUser_weight(String user_weight) {
        this.user_weight = user_weight;
    }

    public String getUser_height() {
        return user_height;
    }

    public void setUser_height(String user_height) {
        this.user_height = user_height;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }
    public String getEmail() {
        return user_email;
    }

    public void setEmail(String email) {
        this.user_email = email;
    }
}
