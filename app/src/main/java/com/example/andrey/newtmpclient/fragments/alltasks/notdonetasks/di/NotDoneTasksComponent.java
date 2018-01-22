package com.example.andrey.newtmpclient.fragments.alltasks.notdonetasks.di;

import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.fragments.alltasks.notdonetasks.NotDoneTasksFragment;

import dagger.Subcomponent;

@Subcomponent(modules = NotDoneTasksModule.class)
@NotDoneTasksScope
public interface NotDoneTasksComponent extends BaseComponent<NotDoneTasksFragment> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<NotDoneTasksComponent, NotDoneTasksModule> {
    }
}
