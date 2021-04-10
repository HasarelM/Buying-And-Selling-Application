package com.dev.hasarelm.buyingselling.Model;

public class orders {

    private int id;
    private int customer_id;
    private int advertisement_id;
    private String order_details;
    private String description_1;
    private String description_2;
    private String description_3;
    private double latitude;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    private String created_at;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private double longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getAdvertisement_id() {
        return advertisement_id;
    }

    public void setAdvertisement_id(int advertisement_id) {
        this.advertisement_id = advertisement_id;
    }

    public String getOrder_details() {
        return order_details;
    }

    public void setOrder_details(String order_details) {
        this.order_details = order_details;
    }

    public String getDescription_1() {
        return description_1;
    }

    public void setDescription_1(String description_1) {
        this.description_1 = description_1;
    }

    public String getDescription_2() {
        return description_2;
    }

    public void setDescription_2(String description_2) {
        this.description_2 = description_2;
    }

    public String getDescription_3() {
        return description_3;
    }

    public void setDescription_3(String description_3) {
        this.description_3 = description_3;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
}
