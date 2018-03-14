package com.example.devendra.firebaseauthui;

/**
 * Created by Devendra on 3/14/2018.
 */

public class BuyerObj {
    String b_id , bname ,blon,blat;

    public BuyerObj()
    {

    }
    public BuyerObj(String b_id, String bname, String blat, String blon) {
        this.b_id = b_id;
        this.bname = bname;
        this.blon = blon;
        this.blat = blat;
    }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBlon() {
        return blon;
    }

    public void setBlon(String blon) {
        this.blon = blon;
    }

    public String getBlat() {
        return blat;
    }

    public void setBlat(String blat) {
        this.blat = blat;
    }
}
