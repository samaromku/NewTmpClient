package com.example.andrey.newtmpclient.storage;

import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.google.gson.Gson;

public class JsonParser {
    public Response parseFromServerUserTasks(String str){
        if(str.startsWith("{")) {
            return new Gson().fromJson(str, Response.class);
        }else return null;
    }

    public String requestToServer(Request request){
        return new Gson().toJson(request);
    }
}
