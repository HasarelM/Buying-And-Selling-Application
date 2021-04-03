package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class ProfileUpdates {

    private ArrayList<profileUpdate>profileUpdate;

    public ArrayList<com.dev.hasarelm.buyingselling.Model.profileUpdate> getProfileUpdate() {
        return profileUpdate;
    }

    public void setProfileUpdate(ArrayList<com.dev.hasarelm.buyingselling.Model.profileUpdate> profileUpdate) {
        this.profileUpdate = profileUpdate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
