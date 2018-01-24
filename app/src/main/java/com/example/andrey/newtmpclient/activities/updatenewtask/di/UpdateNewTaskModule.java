package com.example.andrey.newtmpclient.activities.updatenewtask.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.activities.updatenewtask.UpdateNewTaskView;
import com.example.andrey.newtmpclient.activities.updatenewtask.UpdateNewTaskPresenter;
import com.example.andrey.newtmpclient.activities.updatenewtask.UpdateNewTaskInterActor;
import com.example.andrey.newtmpclient.network.TmpService;

@Module
public class UpdateNewTaskModule implements BaseModule {
    private UpdateNewTaskView view;

    public UpdateNewTaskModule(UpdateNewTaskView view) {
        this.view = view;
    }

    @UpdateNewTaskScope
    @Provides
    public UpdateNewTaskPresenter presenter(UpdateNewTaskInterActor interActor) {
        return new UpdateNewTaskPresenter(view, interActor);
    }

    @UpdateNewTaskScope
    @Provides
    UpdateNewTaskInterActor interActor(TmpService tmpService) {
        return new UpdateNewTaskInterActor(tmpService);
    }
}

