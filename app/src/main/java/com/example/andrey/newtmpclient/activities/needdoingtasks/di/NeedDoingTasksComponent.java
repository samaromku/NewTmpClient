package com.example.andrey.newtmpclient.activities.needdoingtasks.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.activities.needdoingtasks.NeedDoingTasksActivity;

@Subcomponent(modules = NeedDoingTasksModule.class)
@NeedDoingTasksScope
public interface NeedDoingTasksComponent extends BaseComponent<NeedDoingTasksActivity> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<NeedDoingTasksComponent, NeedDoingTasksModule> {
    }
}
