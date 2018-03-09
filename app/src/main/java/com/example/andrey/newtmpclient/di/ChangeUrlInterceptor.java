package com.example.andrey.newtmpclient.di;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by savchenko on 30.01.18.
 */

public class ChangeUrlInterceptor implements Interceptor {
    static final String BASE_URL_OUTER = "http://81.23.123.230:8888";
    private static final String BASE_HOST_OUTER = "81.23.123.230";
    private static final String BASE_HOST_INNER = "192.168.0.98";//tmp
//    private static final String BASE_HOST_INNER = "10.0.2.2";//mine computer emulator
    private static final int PORT = 8443;
    private static final String HTTP = "http";
    private boolean inner;


    public void setInner(boolean inner) {
        this.inner = inner;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl newUrl;
        if(inner){
            newUrl = request.url().newBuilder()
                    .scheme(HTTP)
                    .host(BASE_HOST_INNER)
                    .port(PORT)
                    .build();

        }else {
            newUrl = request.url().newBuilder()
                    .scheme(HTTP)
                    .host(BASE_HOST_OUTER)
                    .port(PORT)
                    .build();
        }
        request = request.newBuilder()
                .url(newUrl)
                .build();

        return chain.proceed(request);
    }
}
