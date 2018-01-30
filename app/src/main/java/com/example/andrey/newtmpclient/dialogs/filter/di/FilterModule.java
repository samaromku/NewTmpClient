package com.example.andrey.newtmpclient.dialogs.filter.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.dialogs.filter.FilterView;
import com.example.andrey.newtmpclient.dialogs.filter.FilterPresenter;
import com.example.andrey.newtmpclient.dialogs.filter.FilterInterActor;

@Module
public class FilterModule implements BaseModule {
    private FilterView view;

    public FilterModule(FilterView view) {
        this.view = view;
    }

    @FilterScope
    @Provides
    public FilterPresenter presenter(FilterInterActor interActor) {
        return new FilterPresenter(view, interActor);
    }

    @FilterScope
    @Provides
    FilterInterActor interActor() {
        return new FilterInterActor();
    }
}

