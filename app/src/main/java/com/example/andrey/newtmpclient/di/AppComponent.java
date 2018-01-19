package com.example.andrey.newtmpclient.di;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Andrey on 23.09.2017.
 */

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {
    void injectComponentManager(ComponentManager componentManager);
}
