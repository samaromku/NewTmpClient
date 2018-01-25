package com.example.andrey.newtmpclient.activities.userrole;

import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.rx.TransformerDialog;
import com.example.andrey.newtmpclient.utils.Utils;

public class NewUserRolePresenter {
    private static final String TAG = NewUserRolePresenter.class.getSimpleName();
    private NewUserRoleView view;
    private NewUserRoleInterActor interActor;

    public NewUserRolePresenter(NewUserRoleView view, NewUserRoleInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void updateUserRole(UserRole userRole){
        interActor.updateUserRole(userRole)
                .compose(new TransformerDialog<>(view))
                .subscribe(response -> {
                    interActor.setUserRoleUpdate().subscribe(() -> view.startMainActivity());
                }, throwable -> Utils.showError(view, throwable));
    }
}
