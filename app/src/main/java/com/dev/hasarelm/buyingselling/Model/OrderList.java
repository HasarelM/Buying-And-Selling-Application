package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class OrderList {

    private ArrayList<orders>orders;

    public ArrayList<com.dev.hasarelm.buyingselling.Model.orders> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<com.dev.hasarelm.buyingselling.Model.orders> orders) {
        this.orders = orders;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
