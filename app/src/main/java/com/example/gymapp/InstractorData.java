package com.example.gymapp;

import java.util.Date;

public class InstractorData {




    private String name;
    private String birthday;
    private String adress;
    private String info;
    private int imageId= R.drawable.ic_launcher_foreground;
    private String id;

    public InstractorData(){}
    public InstractorData(String name, String birthday, String adress, String info, int imageId){
        this.name = name;
        this.birthday = birthday;
        this.adress = adress;
        this.info = info;
        this.imageId = R.drawable.ic_launcher_foreground;
    }
    public InstractorData(InstractorData instractorData){
        this.name = instractorData.name;
        this.birthday = instractorData.birthday;
        this.adress = instractorData.adress;
        this.info = instractorData.info;
        this.imageId = R.drawable.ic_launcher_foreground;
    }
    public String getId(){
        return id;
    }

    public void setId(String id){this.id = id;}

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImageId() {

        return imageId;

    }

    public void setImageId(int imageId) {

        this.imageId = imageId;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public String toString(){
        return id+","+name+","+birthday+","+adress+","+info+","+imageId;
    }


}
