package com.example.devendra.firebaseauthui;

/**
 * Created by Devendra on 3/12/2018.
 */

public class Request {
    String crop_name;
    String f_id;
    String status;
    String image;

    public Request()
    {

    }
    public Request(String crop_name, String f_id, String status, String image) {
        this.crop_name = crop_name;
        this.f_id = f_id;
        this.status = status;
        this.image = image;
    }

    public String getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
