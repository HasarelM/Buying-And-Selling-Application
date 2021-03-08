package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class AllAdvertisementsModel {

    private String message;

    public ArrayList<com.dev.hasarelm.buyingselling.Model.advertisements> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(ArrayList<com.dev.hasarelm.buyingselling.Model.advertisements> advertisements) {
        this.advertisements = advertisements;
    }

    private ArrayList<advertisements>advertisements;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
