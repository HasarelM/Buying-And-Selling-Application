package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class OrderUpdates {

    private ArrayList<orderUpdate>orderUpdate;

    public ArrayList<com.dev.hasarelm.buyingselling.Model.orderUpdate> getOrderUpdate() {
        return orderUpdate;
    }

    public void setOrderUpdate(ArrayList<com.dev.hasarelm.buyingselling.Model.orderUpdate> orderUpdate) {
        this.orderUpdate = orderUpdate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
