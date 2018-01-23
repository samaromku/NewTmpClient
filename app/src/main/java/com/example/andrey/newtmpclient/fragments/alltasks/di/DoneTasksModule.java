package com.example.andrey.newtmpclient.fragments.alltasks.di;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.fragments.alltasks.AllTasksPresenter;
import com.example.andrey.newtmpclient.fragments.alltasks.AllTasksView;
import com.example.andrey.newtmpclient.fragments.alltasks.AllTasksInterActor;
import com.example.andrey.newtmpclient.network.TmpService;

import dagger.Module;
import dagger.Provides;

@Module
public class DoneTasksModule implements BaseModule {
    private AllTasksView view;

    public DoneTasksModule(AllTasksView view) {
        this.view = view;
    }

    @DoneTasksScope
    @Provides
    public AllTasksPresenter presenter(AllTasksInterActor interActor) {
        return new AllTasksPresenter(view, interActor);
    }

    @DoneTasksScope
    @Provides
    AllTasksInterActor interActor(TmpService tmpService) {
        return new AllTasksInterActor(tmpService);
    }
}

