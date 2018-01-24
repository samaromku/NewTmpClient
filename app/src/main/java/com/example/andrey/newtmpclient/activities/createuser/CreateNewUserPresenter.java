package com.example.andrey.newtmpclient.activities.createuser;

import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.rx.TransformerDialog;
import com.example.andrey.newtmpclient.utils.Utils;

import static com.example.andrey.newtmpclient.storage.Const.ERROR_DATA;

public class CreateNewUserPresenter {
    private static final String TAG = CreateNewUserPresenter.class.getSimpleName();
    private CreateNewUserView view;
    private CreateNewUserInterActor interActor;

    public CreateNewUserPresenter(CreateNewUserView view, CreateNewUserInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void createUser(User user){
        interActor.createUser(user)
                .compose(new TransformerDialog<>(view))
                .subscribe(response -> {
                    interActor.addUser(response.getUser()).subscribe();
                    view.startMainActivity();
                }, throwable -> Utils.showError(view, throwable));

    }
}
