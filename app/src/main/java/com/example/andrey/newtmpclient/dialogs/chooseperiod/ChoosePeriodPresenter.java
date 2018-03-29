package com.example.andrey.newtmpclient.dialogs.chooseperiod;

public class ChoosePeriodPresenter {
    private static final String TAG = ChoosePeriodPresenter.class.getSimpleName();
    private ChoosePeriodView view;
    private ChoosePeriodInterActor interActor;

    public ChoosePeriodPresenter(ChoosePeriodView view, ChoosePeriodInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

}
