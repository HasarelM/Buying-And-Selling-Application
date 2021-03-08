package com.dev.hasarelm.buyingselling.Model;

public class advertisements {

    private int id;
    private int category_id;
    private int district_id;
    private int route_id;
    private String title;
    private String description;
    private String sub_description;
    private String date;
    private String from_time;
    private String to_time;
    private String created_at;
    private String category;
    private String district;
    private String vehicle_type;
    private String seller_first_name;
    private String seller_last_name;
    private String seller_phone;
    private String route;

    public String getSeller_first_name() {
        return seller_first_name;
    }

    public void setSeller_first_name(String seller_first_name) {
        this.seller_first_name = seller_first_name;
    }

    public String getSeller_last_name() {
        return seller_last_name;
    }

    public void setSeller_last_name(String seller_last_name) {
        this.seller_last_name = seller_last_name;
    }

    public String getSeller_phone() {
        return seller_phone;
    }

    public void setSeller_phone(String seller_phone) {
        this.seller_phone = seller_phone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSub_description() {
        return sub_description;
    }

    public void setSub_description(String sub_description) {
        this.sub_description = sub_description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }


}
