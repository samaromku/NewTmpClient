package com.example.andrey.newtmpclient.dialogs.chooseperiod;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.dialogs.chooseperiod.di.ChoosePeriodComponent;
import com.example.andrey.newtmpclient.dialogs.chooseperiod.di.ChoosePeriodModule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import javax.inject.Inject;

public class ChoosePeriodFragment extends DialogFragment implements ChoosePeriodView {
    private static final String TAG = ChoosePeriodFragment.class.getSimpleName();
    @Inject
    ChoosePeriodPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("Выбор периода");
        ((ChoosePeriodComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new ChoosePeriodModule(this))).inject(this);
        return inflater.inflate(R.layout.fragment_choose_period, container, false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

}
