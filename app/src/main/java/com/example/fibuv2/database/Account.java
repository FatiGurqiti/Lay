package com.example.fibuv2.database;

public class Account {

    int id;
    int isFirstTime;
    int isLoggedIn;
    int litemode;

    public Account() {
    }
    public Account(int id, int isFirstTime, int isLoggedIn, int litemode) {
        this.id= id;
        this.isFirstTime = isFirstTime;
        this.isLoggedIn = isLoggedIn;
        this.litemode = litemode;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsFirstTime() {
        return isFirstTime;
    }

    public void setIsFirstTime(int isFirstTime) {
        this.isFirstTime = isFirstTime;
    }

    public int getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(int isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public int getLitemode() {
        return litemode;
    }

    public void setLitemode(int litemode) {
        this.litemode = litemode;
    }



}
