package com.example.andrey.newtmpclient.activities.allusers;

public class AllUsersPresenter {
    private static final String TAG = AllUsersPresenter.class.getSimpleName();
    private AllUsersView view;
    private AllUsersInterActor interActor;

    public AllUsersPresenter(AllUsersView view, AllUsersInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

}
