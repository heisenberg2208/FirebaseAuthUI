package com.example.devendra.firebaseauthui;

/**
 * Created by Devendra on 3/14/2018.
 */

public class Farmer {

    String f_id,fname,flon,flat;

    public Farmer()
    {

    }

    public Farmer(String f_id, String fname, String flon, String flat) {
        this.f_id = f_id;
        this.fname = fname;
        this.flon = flon;
        this.flat = flat;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFlon() {
        return flon;
    }

    public void setFlon(String flon) {
        this.flon = flon;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }
}
