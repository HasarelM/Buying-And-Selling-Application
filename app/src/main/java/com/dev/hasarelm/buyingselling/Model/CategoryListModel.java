package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class CategoryListModel {

    private ArrayList<categories>categories;

    public ArrayList<com.dev.hasarelm.buyingselling.Model.categories> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<com.dev.hasarelm.buyingselling.Model.categories> categories) {
        this.categories = categories;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;


}
