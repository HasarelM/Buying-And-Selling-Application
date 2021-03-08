package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class categories {

    private int id;
    private String name;
    private ArrayList<images> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<com.dev.hasarelm.buyingselling.Model.images> getImages() {
        return images;
    }

    public void setImages(ArrayList<com.dev.hasarelm.buyingselling.Model.images> images) {
        this.images = images;
    }


}
