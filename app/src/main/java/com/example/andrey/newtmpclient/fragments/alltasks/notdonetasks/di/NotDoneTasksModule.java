package com.example.andrey.newtmpclient.fragments.alltasks.notdonetasks.di;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.fragments.alltasks.DoneOrNotView;
import com.example.andrey.newtmpclient.fragments.alltasks.donetasks.DoneTasksInterActor;
import com.example.andrey.newtmpclient.fragments.alltasks.donetasks.DoneTasksPresenter;
import com.example.andrey.newtmpclient.fragments.alltasks.notdonetasks.NotDoneTasksView;
import com.example.andrey.newtmpclient.network.TmpService;

import dagger.Module;
import dagger.Provides;

@Module
public class NotDoneTasksModule implements BaseModule {
    private DoneOrNotView view;

    public NotDoneTasksModule(NotDoneTasksView view) {
        this.view = view;
    }

    @NotDoneTasksScope
    @Provides
    public DoneTasksPresenter presenter(DoneTasksInterActor interActor) {
        return new DoneTasksPresenter(view, interActor);
    }

    @NotDoneTasksScope
    @Provides
    DoneTasksInterActor interActor(TmpService tmpService) {
        return new DoneTasksInterActor(tmpService);
    }
}

