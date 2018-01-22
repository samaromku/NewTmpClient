package com.example.andrey.newtmpclient.fragments.notdonetasks.di;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.fragments.notdonetasks.NotDoneTasksInterActor;
import com.example.andrey.newtmpclient.fragments.notdonetasks.NotDoneTasksPresenter;
import com.example.andrey.newtmpclient.fragments.notdonetasks.NotDoneTasksView;
import com.example.andrey.newtmpclient.network.TmpService;

import dagger.Module;
import dagger.Provides;

@Module
public class NotDoneTasksModule implements BaseModule {
    private NotDoneTasksView view;

    public NotDoneTasksModule(NotDoneTasksView view) {
        this.view = view;
    }

    @NotDoneTasksScope
    @Provides
    public NotDoneTasksPresenter presenter(NotDoneTasksInterActor interActor) {
        return new NotDoneTasksPresenter(view, interActor);
    }

    @NotDoneTasksScope
    @Provides
    NotDoneTasksInterActor interActor(TmpService tmpService) {
        return new NotDoneTasksInterActor(tmpService);
    }
}

