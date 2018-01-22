package com.example.andrey.newtmpclient.activities.createTask.di;

import com.example.andrey.newtmpclient.activities.createTask.CreateTaskInteractorImpl;
import com.example.andrey.newtmpclient.activities.createTask.CreateTaskPresenterImpl;
import com.example.andrey.newtmpclient.activities.createTask.CreateTaskView;
import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.network.TmpService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by savchenko on 21.01.18.
 */
@Module
public class CreateTaskModule implements BaseModule{
    private CreateTaskView view;

    public CreateTaskModule(CreateTaskView view) {
        this.view = view;
    }

    @Provides
    @CreateTaskScope
    CreateTaskPresenterImpl presenter(CreateTaskInteractorImpl interactor){
        return new CreateTaskPresenterImpl(view, interactor);
    }

    @Provides
    @CreateTaskScope
    CreateTaskInteractorImpl interactor(TmpService tmpService){
        return new CreateTaskInteractorImpl(tmpService);
    }
}
