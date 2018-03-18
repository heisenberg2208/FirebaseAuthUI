package com.example.devendra.firebaseauthui;

/**
 * Created by Devendra on 3/16/2018.
 */

public class Link {

    String b_id , f_id , c_id;
    public Link()
    {

    }

    public Link(String b_id, String f_id, String c_id) {
        this.b_id = b_id;
        this.f_id = f_id;
        this.c_id = c_id;
    }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }
}
