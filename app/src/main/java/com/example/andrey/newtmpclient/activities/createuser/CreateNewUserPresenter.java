package com.example.andrey.newtmpclient.activities.createuser;

public class CreateNewUserPresenter {
    private static final String TAG = CreateNewUserPresenter.class.getSimpleName();
    private CreateNewUserView view;
    private CreateNewUserInterActor interActor;

    public CreateNewUserPresenter(CreateNewUserView view, CreateNewUserInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

}
