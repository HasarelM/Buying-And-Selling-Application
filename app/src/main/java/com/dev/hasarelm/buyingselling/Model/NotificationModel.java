package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class NotificationModel {

    private ArrayList<notifications> notifications;
    private String message;

    public ArrayList<notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<notifications> notifications) {
        this.notifications = notifications;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
