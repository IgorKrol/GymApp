package com.example.gymapp;


/**
 * Device Object
 */
public class DeviceData {

    private String id;
    private String name;
    private int imageId;
    private String url;

    public DeviceData(String id, String name, String url, int imageId) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
        this.url = url;
    }

    public DeviceData(String name, String url, int imageId) {
        this.name = name;
        this.imageId = imageId;
        this.url = url;
    }

    public DeviceData(){}

    public DeviceData(DeviceData dd){
        this.id = dd.id;
        this.name = dd.name;
        this.imageId = dd.imageId;
        this.url = dd.url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DeviceData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageId=" + imageId +
                ", url='" + url + '\'' +
                '}';
    }
}
