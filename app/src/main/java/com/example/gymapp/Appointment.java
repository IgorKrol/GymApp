package com.example.gymapp;

public class Appointment {
    private String date;
    private String instructor_id;
    private String user_id;

    public Appointment() {
    }
    public Appointment(Appointment appointment) {
        this.date = appointment.date;
        this.instructor_id = appointment.instructor_id;
        this.user_id = appointment.user_id;
    }
    public Appointment(String date, String instructor_id, String user_id) {
        this.date = date;
        this.instructor_id = instructor_id;
        this.user_id = user_id;
    }

    public String getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(String instructor_id) {
        this.instructor_id = instructor_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
