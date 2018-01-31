
package com.example.andrey.newtmpclient.entities.map;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Step {

    @SerializedName("distance")
    private Distance Distance;
    @SerializedName("duration")
    private Duration Duration;
    @SerializedName("end_location")
    private EndLocation EndLocation;
    @SerializedName("html_instructions")
    private String HtmlInstructions;
    @SerializedName("polyline")
    private Polyline Polyline;
    @SerializedName("start_location")
    private StartLocation StartLocation;
    @SerializedName("travel_mode")
    private String TravelMode;

    public Distance getDistance() {
        return Distance;
    }

    public void setDistance(Distance distance) {
        Distance = distance;
    }

    public Duration getDuration() {
        return Duration;
    }

    public void setDuration(Duration duration) {
        Duration = duration;
    }

    public EndLocation getEndLocation() {
        return EndLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        EndLocation = endLocation;
    }

    public String getHtmlInstructions() {
        return HtmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        HtmlInstructions = htmlInstructions;
    }

    public Polyline getPolyline() {
        return Polyline;
    }

    public void setPolyline(Polyline polyline) {
        Polyline = polyline;
    }

    public StartLocation getStartLocation() {
        return StartLocation;
    }

    public void setStartLocation(StartLocation startLocation) {
        StartLocation = startLocation;
    }

    public String getTravelMode() {
        return TravelMode;
    }

    public void setTravelMode(String travelMode) {
        TravelMode = travelMode;
    }

}
