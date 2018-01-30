package com.example.andrey.newtmpclient.dialogs.filter;

public class FilterPresenter {
    private static final String TAG = FilterPresenter.class.getSimpleName();
    private FilterView view;
    private FilterInterActor interActor;

    public FilterPresenter(FilterView view, FilterInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

}
