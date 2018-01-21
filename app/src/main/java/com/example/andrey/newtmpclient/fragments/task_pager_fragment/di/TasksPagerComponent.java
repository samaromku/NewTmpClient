package com.example.andrey.newtmpclient.fragments.task_pager_fragment.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.fragments.task_pager_fragment.TasksPagerFragment;

@Subcomponent(modules = TasksPagerModule.class)
@TasksPagerScope
public interface TasksPagerComponent extends BaseComponent<TasksPagerFragment> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<TasksPagerComponent, TasksPagerModule> {
    }
}
