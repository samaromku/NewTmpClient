package com.example.andrey.newtmpclient.fragments.task_pager_fragment.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.fragments.task_pager_fragment.TasksPagerView;
import com.example.andrey.newtmpclient.fragments.task_pager_fragment.TasksPagerPresenter;
import com.example.andrey.newtmpclient.fragments.task_pager_fragment.TasksPagerInterActor;
import com.example.andrey.newtmpclient.network.TmpService;

@Module
public class TasksPagerModule implements BaseModule {
    private TasksPagerView view;

    public TasksPagerModule(TasksPagerView view) {
        this.view = view;
    }

    @TasksPagerScope
    @Provides
    public TasksPagerPresenter presenter(TasksPagerInterActor interActor) {
        return new TasksPagerPresenter(view, interActor);
    }

    @TasksPagerScope
    @Provides
    TasksPagerInterActor interActor(TmpService tmpService) {
        return new TasksPagerInterActor(tmpService);
    }
}

