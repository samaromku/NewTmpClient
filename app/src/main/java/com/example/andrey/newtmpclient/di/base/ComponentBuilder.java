package com.example.andrey.newtmpclient.di.base;

/**
 * Created by savchenko on 27.12.17.
 */

public interface ComponentBuilder<C extends BaseComponent, T extends BaseModule> {
    C build();

    ComponentBuilder<C, T> module(T module);
}
