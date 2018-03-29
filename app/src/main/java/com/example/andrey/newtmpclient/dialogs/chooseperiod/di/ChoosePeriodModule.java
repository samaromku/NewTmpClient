package com.example.andrey.newtmpclient.dialogs.chooseperiod.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.dialogs.chooseperiod.ChoosePeriodView;
import com.example.andrey.newtmpclient.dialogs.chooseperiod.ChoosePeriodPresenter;
import com.example.andrey.newtmpclient.dialogs.chooseperiod.ChoosePeriodInterActor;

@Module
public class ChoosePeriodModule implements BaseModule {
    private ChoosePeriodView view;

    public ChoosePeriodModule(ChoosePeriodView view) {
        this.view = view;
    }

    @ChoosePeriodScope
    @Provides
    public ChoosePeriodPresenter presenter(ChoosePeriodInterActor interActor) {
        return new ChoosePeriodPresenter(view, interActor);
    }

    @ChoosePeriodScope
    @Provides
    ChoosePeriodInterActor interActor() {
        return new ChoosePeriodInterActor();
    }
}

