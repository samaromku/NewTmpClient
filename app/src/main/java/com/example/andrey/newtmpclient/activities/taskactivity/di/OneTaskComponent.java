package com.example.andrey.newtmpclient.activities.taskactivity.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.activities.taskactivity.OneTaskActivity;

@Subcomponent(modules = OneTaskModule.class)
@OneTaskScope
public interface OneTaskComponent extends BaseComponent<OneTaskActivity> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<OneTaskComponent, OneTaskModule> {
    }
}
