package com.dev.hasarelm.buyingselling.Model;

import java.util.ArrayList;

public class VehicleTypeModel {

   private ArrayList<vehicleTypes>vehicleTypes;

    public ArrayList<com.dev.hasarelm.buyingselling.Model.vehicleTypes> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(ArrayList<com.dev.hasarelm.buyingselling.Model.vehicleTypes> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
