package com.example.andrey.newtmpclient.fragments.alltasks.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.fragments.alltasks.AllTasksView;
import com.example.andrey.newtmpclient.fragments.alltasks.AllTasksPresenter;
import com.example.andrey.newtmpclient.fragments.alltasks.AllTasksInterActor;

@Module
public class AllTasksModule implements BaseModule {
    private AllTasksView view;

    public AllTasksModule(AllTasksView view) {
        this.view = view;
    }

    @AllTasksScope
    @Provides
    public AllTasksPresenter presenter(AllTasksInterActor interActor) {
        return new AllTasksPresenter(view, interActor);
    }

    @AllTasksScope
    @Provides
    AllTasksInterActor interActor() {
        return new AllTasksInterActor();
    }
}

