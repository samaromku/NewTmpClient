package com.example.andrey.newtmpclient.service.period.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.service.period.PeriodService;

@Subcomponent(modules = PeriodModule.class)
@PeriodScope
public interface PeriodComponent extends BaseComponent<PeriodService> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<PeriodComponent, PeriodModule> {
    }
}
