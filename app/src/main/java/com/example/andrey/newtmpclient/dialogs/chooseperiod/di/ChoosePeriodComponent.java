package com.example.andrey.newtmpclient.dialogs.chooseperiod.di;

import dagger.Subcomponent;

import com.example.andrey.newtmpclient.di.base.ComponentBuilder;
import com.example.andrey.newtmpclient.di.base.BaseComponent;
import com.example.andrey.newtmpclient.dialogs.chooseperiod.ChoosePeriodDialog;

@Subcomponent(modules = ChoosePeriodModule.class)
@ChoosePeriodScope
public interface ChoosePeriodComponent extends BaseComponent<ChoosePeriodDialog> {
    @Subcomponent.Builder
    interface Builder extends ComponentBuilder<ChoosePeriodComponent, ChoosePeriodModule> {
    }
}
