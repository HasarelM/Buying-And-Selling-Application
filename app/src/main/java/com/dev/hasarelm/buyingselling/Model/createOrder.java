package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class createOrder {

    private ArrayList<orderCreate>orderCreate;

    public ArrayList<com.dev.hasarelm.buyingselling.Model.orderCreate> getOrderCreate() {
        return orderCreate;
    }

    public void setOrderCreate(ArrayList<com.dev.hasarelm.buyingselling.Model.orderCreate> orderCreate) {
        this.orderCreate = orderCreate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
