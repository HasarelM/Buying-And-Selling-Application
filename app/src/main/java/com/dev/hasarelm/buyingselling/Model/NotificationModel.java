package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class NotificationModel {

    private ArrayList<notifications> notifications;

    public ArrayList<com.dev.hasarelm.buyingselling.Model.notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<com.dev.hasarelm.buyingselling.Model.notifications> notifications) {
        this.notifications = notifications;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
