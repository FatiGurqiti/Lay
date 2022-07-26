package com.fdev.lay.database;

public class Account {

    int id;
    int isFirstTime;
    int isLoggedIn;
    int litemode;


    int showSeenContents;

    public Account() {
    }

    public Account(int id, int isFirstTime, int isLoggedIn, int litemode, int showSeenContents) {
        this.id = id;
        this.isFirstTime = isFirstTime;
        this.isLoggedIn = isLoggedIn;
        this.litemode = litemode;
        this.showSeenContents = showSeenContents;
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

    public int getShowSeenContents() {
        return showSeenContents;
    }

    public void setShowSeenContents(int showSeenContents) {
        this.showSeenContents = showSeenContents;
    }

}
