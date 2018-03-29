package com.example.andrey.newtmpclient.dialogs.chooseperiod;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.dialogs.chooseperiod.di.ChoosePeriodComponent;
import com.example.andrey.newtmpclient.dialogs.chooseperiod.di.ChoosePeriodModule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

public class ChoosePeriodDialog extends DialogFragment implements ChoosePeriodView {
    private static final String TAG = ChoosePeriodDialog.class.getSimpleName();
    @Inject
    ChoosePeriodPresenter presenter;
    @BindView(R.id.btnChooseDateFirst)Button btnChooseDateFirst;
    @BindView(R.id.btnChooseTimeFirst)Button btnChooseTimeFirst;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("Выбор периода");
        ((ChoosePeriodComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new ChoosePeriodModule(this))).inject(this);
        return inflater.inflate(R.layout.fragment_choose_period, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        RadioRealButtonGroup rgChoosePeriod = view.findViewById(R.id.rgChoosePeriod);
        rgChoosePeriod.setOnPositionChangedListener((button, currentPosition, lastPosition) -> {
            if(currentPosition==1){
                btnChooseDateFirst.setVisibility(View.GONE);
            }else {
                btnChooseDateFirst.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

}
