
package com.example.andrey.newtmpclient.entities.map;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Leg {

    @SerializedName("distance")
    private Distance Distance;
    @SerializedName("duration")
    private Duration Duration;
    @SerializedName("end_address")
    private String EndAddress;
    @SerializedName("end_location")
    private EndLocation EndLocation;
    @SerializedName("start_address")
    private String StartAddress;
    @SerializedName("start_location")
    private StartLocation StartLocation;
    @SerializedName("steps")
    private List<Step> Steps;
    @SerializedName("traffic_speed_entry")
    private List<Object> TrafficSpeedEntry;
    @SerializedName("via_waypoint")
    private List<Object> ViaWaypoint;

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

    public String getEndAddress() {
        return EndAddress;
    }

    public void setEndAddress(String endAddress) {
        EndAddress = endAddress;
    }

    public EndLocation getEndLocation() {
        return EndLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        EndLocation = endLocation;
    }

    public String getStartAddress() {
        return StartAddress;
    }

    public void setStartAddress(String startAddress) {
        StartAddress = startAddress;
    }

    public StartLocation getStartLocation() {
        return StartLocation;
    }

    public void setStartLocation(StartLocation startLocation) {
        StartLocation = startLocation;
    }

    public List<Step> getSteps() {
        return Steps;
    }

    public void setSteps(List<Step> steps) {
        Steps = steps;
    }

    public List<Object> getTrafficSpeedEntry() {
        return TrafficSpeedEntry;
    }

    public void setTrafficSpeedEntry(List<Object> trafficSpeedEntry) {
        TrafficSpeedEntry = trafficSpeedEntry;
    }

    public List<Object> getViaWaypoint() {
        return ViaWaypoint;
    }

    public void setViaWaypoint(List<Object> viaWaypoint) {
        ViaWaypoint = viaWaypoint;
    }

}
