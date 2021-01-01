package com.example.seniorproject;

import java.io.Serializable;

public class Oy implements Serializable
{

    String userId;
    int pOy, nOy;

    public Oy() { }

    public Oy(String userId, int pOy, int nOy) {
        this.userId = userId;
        this.pOy = pOy;
        this.nOy = nOy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getpOy() {
        return pOy;
    }

    public void setpOy(int pOy) {
        this.pOy = pOy;
    }

    public int getnOy() {
        return nOy;
    }

    public void setnOy(int nOy) {
        this.nOy = nOy;
    }
}
