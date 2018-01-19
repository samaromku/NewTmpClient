package com.example.andrey.newtmpclient.fragments.alltasks.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.fragments.alltasks.AllTasksFragment;

@Subcomponent(modules = AllTasksModule.class)
@AllTasksScope
public interface AllTasksComponent extends BaseComponent<AllTasksFragment> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<AllTasksComponent, AllTasksModule> {
    }
}
