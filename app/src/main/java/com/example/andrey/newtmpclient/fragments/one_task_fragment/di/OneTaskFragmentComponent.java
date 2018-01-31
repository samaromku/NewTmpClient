package com.example.andrey.newtmpclient.fragments.one_task_fragment.di;

import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.fragments.one_task_fragment.OneTaskPageFragment;

import dagger.Subcomponent;

/**
 * Created by savchenko on 31.01.18.
 */
@Subcomponent(modules = OneTaskFragmentModule.class)
@OneTaskFragmentScope
public interface OneTaskFragmentComponent extends BaseComponent<OneTaskPageFragment>{
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<OneTaskFragmentComponent, OneTaskFragmentModule>{}
}
