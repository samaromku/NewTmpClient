package com.example.andrey.newtmpclient.fragments.users;

public class UsersMvpPresenter {
    private static final String TAG = UsersMvpPresenter.class.getSimpleName();
    private UsersMvpView view;
    private UsersMvpInterActor interActor;

    public UsersMvpPresenter(UsersMvpView view, UsersMvpInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void getListFroAdapter() {
        interActor.getListFroAdapter()
                .subscribe(list -> view.setListToAdapter(list));
    }
}
