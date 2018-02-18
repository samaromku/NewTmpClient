package com.example.andrey.newtmpclient.dialogs.directions.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.dialogs.directions.DirectionsView;
import com.example.andrey.newtmpclient.dialogs.directions.DirectionsPresenter;
import com.example.andrey.newtmpclient.dialogs.directions.DirectionsInterActor;

@Module
public class DirectionsModule implements BaseModule {
    private DirectionsView view;

    public DirectionsModule(DirectionsView view) {
        this.view = view;
    }

    @DirectionsScope
    @Provides
    public DirectionsPresenter presenter(DirectionsInterActor interActor) {
        return new DirectionsPresenter(view, interActor);
    }

    @DirectionsScope
    @Provides
    DirectionsInterActor interActor() {
        return new DirectionsInterActor();
    }
}

