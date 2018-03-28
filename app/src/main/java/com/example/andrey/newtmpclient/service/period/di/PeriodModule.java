package com.example.andrey.newtmpclient.service.period.di;

import dagger.Module;
import dagger.Provides;

import com.example.andrey.newtmpclient.di.base.BaseModule;
import com.example.andrey.newtmpclient.service.period.PeriodView;
import com.example.andrey.newtmpclient.service.period.PeriodPresenter;
import com.example.andrey.newtmpclient.service.period.PeriodInterActor;

@Module
public class PeriodModule implements BaseModule {
    private PeriodView view;

    public PeriodModule(PeriodView view) {
        this.view = view;
    }

    @PeriodScope
    @Provides
    public PeriodPresenter presenter(PeriodInterActor interActor) {
        return new PeriodPresenter(view, interActor);
    }

    @PeriodScope
    @Provides
    PeriodInterActor interActor() {
        return new PeriodInterActor();
    }
}

