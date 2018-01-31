package com.example.andrey.newtmpclient.fragments.one_task_fragment.di;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.fragments.one_task_fragment.OneTaskFragmentInterActor;
import com.example.andrey.newtmpclient.fragments.one_task_fragment.OneTaskFragmentPresenter;
import com.example.andrey.newtmpclient.fragments.one_task_fragment.OneTaskView;
import com.example.andrey.newtmpclient.network.TmpService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by savchenko on 31.01.18.
 */
@Module
public class OneTaskFragmentModule implements BaseModule{
    private OneTaskView view;

    public OneTaskFragmentModule(OneTaskView view) {
        this.view = view;
    }

    @Provides
    @OneTaskFragmentScope
    OneTaskFragmentPresenter presenter(OneTaskFragmentInterActor interActor){
        return new OneTaskFragmentPresenter(view, interActor);
    }


    @Provides
    @OneTaskFragmentScope
    OneTaskFragmentInterActor interActor(TmpService tmpService){
        return new OneTaskFragmentInterActor(tmpService);
    }


}
