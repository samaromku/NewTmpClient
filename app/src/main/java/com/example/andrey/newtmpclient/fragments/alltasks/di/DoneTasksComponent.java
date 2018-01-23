package com.example.andrey.newtmpclient.fragments.alltasks.di;

import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.fragments.alltasks.AllTasksFragment;

import dagger.Subcomponent;

@Subcomponent(modules = DoneTasksModule.class)
@DoneTasksScope
public interface DoneTasksComponent extends BaseComponent<AllTasksFragment> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<DoneTasksComponent, DoneTasksModule> {
    }
}
