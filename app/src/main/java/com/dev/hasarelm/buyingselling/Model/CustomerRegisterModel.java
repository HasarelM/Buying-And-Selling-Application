package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class CustomerRegisterModel {

    private ArrayList<register> register;

    public ArrayList<com.dev.hasarelm.buyingselling.Model.register> getRegister() {
        return register;
    }

    public void setRegister(ArrayList<com.dev.hasarelm.buyingselling.Model.register> register) {
        this.register = register;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
