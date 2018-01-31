
package com.example.andrey.newtmpclient.entities.map;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Bounds {

    @SerializedName("northeast")
    private Northeast Northeast;
    @SerializedName("southwest")
    private Southwest Southwest;

    public Northeast getNortheast() {
        return Northeast;
    }

    public void setNortheast(Northeast northeast) {
        Northeast = northeast;
    }

    public Southwest getSouthwest() {
        return Southwest;
    }

    public void setSouthwest(Southwest southwest) {
        Southwest = southwest;
    }

}
