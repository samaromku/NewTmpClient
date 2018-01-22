package com.example.andrey.newtmpclient.fragments.alltasks.old;

public class AllTasksPresenter {
    private static final String TAG = AllTasksPresenter.class.getSimpleName();
    private AllTasksView view;
    private AllTasksInterActor interActor;

    public AllTasksPresenter(AllTasksView view, AllTasksInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

}
