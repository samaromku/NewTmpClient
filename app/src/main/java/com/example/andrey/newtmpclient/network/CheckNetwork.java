package com.example.andrey.newtmpclient.network;

public class CheckNetwork {
    private boolean isNetworkInsideOrOutside;

    public boolean isNetworkInsideOrOutside() {
        return isNetworkInsideOrOutside;
    }

    public void setNetworkInsideOrOutside(boolean networkInsideOrOutside) {
        isNetworkInsideOrOutside = networkInsideOrOutside;
    }

    public static final CheckNetwork instance = new CheckNetwork();
    private CheckNetwork(){}
}
