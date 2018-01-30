package com.example.andrey.newtmpclient.activities.taskactivity.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.activities.taskactivity.OneTaskView;
import com.example.andrey.newtmpclient.activities.taskactivity.OneTaskPresenter;
import com.example.andrey.newtmpclient.activities.taskactivity.OneTaskInterActor;
import com.example.andrey.newtmpclient.network.TmpService;

@Module
public class OneTaskModule implements BaseModule {
    private OneTaskView view;

    public OneTaskModule(OneTaskView view) {
        this.view = view;
    }

    @OneTaskScope
    @Provides
    public OneTaskPresenter presenter(OneTaskInterActor interActor) {
        return new OneTaskPresenter(view, interActor);
    }

    @OneTaskScope
    @Provides
    OneTaskInterActor interActor(TmpService tmpService) {
        return new OneTaskInterActor(tmpService);
    }
}

