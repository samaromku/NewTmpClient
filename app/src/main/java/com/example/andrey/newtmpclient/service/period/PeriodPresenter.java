package com.example.andrey.newtmpclient.service.period;

public class PeriodPresenter {
    private static final String TAG = PeriodPresenter.class.getSimpleName();
    private PeriodView view;
    private PeriodInterActor interActor;

    public PeriodPresenter(PeriodView view, PeriodInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

}
