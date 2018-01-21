package com.example.andrey.newtmpclient.createTask.di;

import com.example.andrey.newtmpclient.createTask.CreateTaskActivity;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.di.base.ComponentBuilder;

import dagger.Subcomponent;

/**
 * Created by savchenko on 21.01.18.
 */
@Subcomponent(modules = CreateTaskModule.class)
@CreateTaskScope
public interface CreateTaskComponent extends BaseComponent<CreateTaskActivity>{
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<CreateTaskComponent, CreateTaskModule>{}

}
