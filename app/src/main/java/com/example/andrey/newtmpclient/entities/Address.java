package com.example.andrey.newtmpclient.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Address extends RealmObject{
    @PrimaryKey
    private int id;
    private String name;
    private String address;
    private String lat;
    private String lng;

    public Address(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Address(String name, String address) {
        super();
        this.name = name;
        this.address = address;
    }

    public Address(int id, String name, String address, String coordsLat, String coordsLon) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lat = coordsLat;
        this.lng = coordsLon;
    }

    public Address(){}

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Address [id=" + id + ", name=" + name + ", address=" + address + ", lat=" + lat
                + ", lng=" + lng + "]";
    }


}