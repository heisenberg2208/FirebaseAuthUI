package com.example.devendra.firebaseauthui;

/**
 * Created by Devendra on 3/15/2018.
 */

public class User {

    String u_id,uname , ulat , ulon,add;

    public User()
    {

    }

    public User(String u_id, String uname, String ulat, String ulon,String add) {
        this.u_id = u_id;
        this.uname = uname;
        this.ulat = ulat;
        this.ulon = ulon;
        this.add = add;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUlat() {
        return ulat;
    }

    public void setUlat(String ulat) {
        this.ulat = ulat;
    }

    public String getUlon() {
        return ulon;
    }

    public void setUlon(String ulon) {
        this.ulon = ulon;
    }
}
