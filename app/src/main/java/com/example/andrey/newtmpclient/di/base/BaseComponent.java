package com.example.andrey.newtmpclient.di.base;

/**
 * Created by savchenko on 27.12.17.
 */

public interface BaseComponent<T> {
    void inject(T activity);
}
