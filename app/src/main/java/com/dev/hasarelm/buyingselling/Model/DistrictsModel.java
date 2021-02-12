package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class DistrictsModel {

    public ArrayList<com.dev.hasarelm.buyingselling.Model.districtTypes> getDistrictTypes() {
        return districtTypes;
    }

    public void setDistrictTypes(ArrayList<com.dev.hasarelm.buyingselling.Model.districtTypes> districtTypes) {
        this.districtTypes = districtTypes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private ArrayList<districtTypes> districtTypes;
    private String message;
}
