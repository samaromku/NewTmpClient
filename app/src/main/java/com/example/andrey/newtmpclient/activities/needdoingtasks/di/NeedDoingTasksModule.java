package com.example.andrey.newtmpclient.activities.needdoingtasks.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.activities.needdoingtasks.NeedDoingTasksView;
import com.example.andrey.newtmpclient.activities.needdoingtasks.NeedDoingTasksPresenter;
import com.example.andrey.newtmpclient.activities.needdoingtasks.NeedDoingTasksInterActor;
import com.example.andrey.newtmpclient.network.TmpService;

@Module
public class NeedDoingTasksModule implements BaseModule {
    private NeedDoingTasksView view;

    public NeedDoingTasksModule(NeedDoingTasksView view) {
        this.view = view;
    }

    @NeedDoingTasksScope
    @Provides
    public NeedDoingTasksPresenter presenter(NeedDoingTasksInterActor interActor) {
        return new NeedDoingTasksPresenter(view, interActor);
    }

    @NeedDoingTasksScope
    @Provides
    NeedDoingTasksInterActor interActor(TmpService tmpService) {
        return new NeedDoingTasksInterActor(tmpService);
    }
}

