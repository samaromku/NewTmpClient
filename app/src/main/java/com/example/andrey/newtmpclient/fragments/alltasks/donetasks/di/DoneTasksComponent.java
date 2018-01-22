package com.example.andrey.newtmpclient.fragments.alltasks.donetasks.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.fragments.alltasks.donetasks.DoneTasksFragment;

@Subcomponent(modules = DoneTasksModule.class)
@DoneTasksScope
public interface DoneTasksComponent extends BaseComponent<DoneTasksFragment> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<DoneTasksComponent, DoneTasksModule> {
    }
}
