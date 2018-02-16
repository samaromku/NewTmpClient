package com.example.andrey.newtmpclient.network;

public class Client {
    public static final Client INSTANCE = new Client();

    private boolean auth = false;
    private boolean isServerConnection;

    public boolean isServerConnection() {
        return isServerConnection;
    }

    void setServerConnection(boolean serverConnection) {
        isServerConnection = serverConnection;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }
}
