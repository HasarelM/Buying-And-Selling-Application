package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class RouteModel {

    private ArrayList<routes>routes;

    public ArrayList<com.dev.hasarelm.buyingselling.Model.routes> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<com.dev.hasarelm.buyingselling.Model.routes> routes) {
        this.routes = routes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
