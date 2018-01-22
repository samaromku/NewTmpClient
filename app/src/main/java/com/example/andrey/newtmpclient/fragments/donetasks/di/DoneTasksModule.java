package com.example.andrey.newtmpclient.fragments.donetasks.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.fragments.donetasks.DoneTasksInterActor;
import com.example.andrey.newtmpclient.fragments.donetasks.DoneTasksView;
import com.example.andrey.newtmpclient.fragments.donetasks.DoneTasksPresenter;
import com.example.andrey.newtmpclient.network.TmpService;

@Module
public class DoneTasksModule implements BaseModule {
    private DoneTasksView view;

    public DoneTasksModule(DoneTasksView view) {
        this.view = view;
    }

    @DoneTasksScope
    @Provides
    public DoneTasksPresenter presenter(DoneTasksInterActor interActor) {
        return new DoneTasksPresenter(view, interActor);
    }

    @DoneTasksScope
    @Provides
    DoneTasksInterActor interActor(TmpService tmpService) {
        return new DoneTasksInterActor(tmpService);
    }
}

