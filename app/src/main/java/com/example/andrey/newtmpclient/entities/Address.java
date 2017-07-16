package com.example.andrey.newtmpclient.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Address extends RealmObject{
    @PrimaryKey
    private int id;
    private String name;
    private String address;
    private String coordsLat;
    private String coordsLon;

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
        this.coordsLat = coordsLat;
        this.coordsLon = coordsLon;
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

    public String getCoordsLat() {
        return coordsLat;
    }

    public void setCoordsLat(String coordsLat) {
        this.coordsLat = coordsLat;
    }

    public String getCoordsLon() {
        return coordsLon;
    }

    public void setCoordsLon(String coordsLon) {
        this.coordsLon = coordsLon;
    }

    @Override
    public String toString() {
        return "Address [id=" + id + ", name=" + name + ", address=" + address + ", coordsLat=" + coordsLat
                + ", coordsLon=" + coordsLon + "]";
    }


}