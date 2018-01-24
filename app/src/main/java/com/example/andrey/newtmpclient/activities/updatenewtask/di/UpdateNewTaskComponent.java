package com.example.andrey.newtmpclient.activities.updatenewtask.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.activities.updatenewtask.UpdateNewTaskActivity;

@Subcomponent(modules = UpdateNewTaskModule.class)
@UpdateNewTaskScope
public interface UpdateNewTaskComponent extends BaseComponent<UpdateNewTaskActivity> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<UpdateNewTaskComponent, UpdateNewTaskModule> {
    }
}
