package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class DeleteSellerAdd {
    private ArrayList<advertisementDelete>advertisementDelete;

    public ArrayList<com.dev.hasarelm.buyingselling.Model.advertisementDelete> getAdvertisementDelete() {
        return advertisementDelete;
    }

    public void setAdvertisementDelete(ArrayList<com.dev.hasarelm.buyingselling.Model.advertisementDelete> advertisementDelete) {
        this.advertisementDelete = advertisementDelete;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
