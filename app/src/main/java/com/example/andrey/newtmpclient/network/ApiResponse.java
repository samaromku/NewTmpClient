package com.example.andrey.newtmpclient.network;

/**
 * Created by savchenko on 15.02.18.
 */

public class ApiResponse<T> {
    private String response;
    private T data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
