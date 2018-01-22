package com.example.andrey.newtmpclient.fragments.alltasks.old.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.fragments.alltasks.old.AllTasksView;
import com.example.andrey.newtmpclient.fragments.alltasks.old.AllTasksPresenter;
import com.example.andrey.newtmpclient.fragments.alltasks.old.AllTasksInterActor;

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

