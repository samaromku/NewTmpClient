
package com.example.andrey.newtmpclient.entities.map;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Route {

    @SerializedName("bounds")
    private Bounds Bounds;
    @SerializedName("copyrights")
    private String Copyrights;
    @SerializedName("legs")
    private List<Leg> Legs;
    @SerializedName("overview_polyline")
    private OverviewPolyline OverviewPolyline;
    @SerializedName("summary")
    private String Summary;
    @SerializedName("warnings")
    private List<Object> Warnings;
    @SerializedName("waypoint_order")
    private List<Object> WaypointOrder;

    public Bounds getBounds() {
        return Bounds;
    }

    public void setBounds(Bounds bounds) {
        Bounds = bounds;
    }

    public String getCopyrights() {
        return Copyrights;
    }

    public void setCopyrights(String copyrights) {
        Copyrights = copyrights;
    }

    public List<Leg> getLegs() {
        return Legs;
    }

    public void setLegs(List<Leg> legs) {
        Legs = legs;
    }

    public OverviewPolyline getOverviewPolyline() {
        return OverviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        OverviewPolyline = overviewPolyline;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public List<Object> getWarnings() {
        return Warnings;
    }

    public void setWarnings(List<Object> warnings) {
        Warnings = warnings;
    }

    public List<Object> getWaypointOrder() {
        return WaypointOrder;
    }

    public void setWaypointOrder(List<Object> waypointOrder) {
        WaypointOrder = waypointOrder;
    }

}
