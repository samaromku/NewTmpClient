package com.example.andrey.newtmpclient.activities.oneuser;

import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.rx.TransformerDialog;
import com.example.andrey.newtmpclient.utils.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.andrey.newtmpclient.storage.Const.ERROR_DATA;

public class OneUserPresenter {
    private static final String TAG = OneUserPresenter.class.getSimpleName();
    private OneUserView view;
    private OneUserInterActor interActor;

    public OneUserPresenter(OneUserView view, OneUserInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void removeUser(User user) {
        interActor.removeUser(user)
                .compose(new TransformerDialog<>(view))
                .subscribe(response -> {
                    interActor.setRemoveUser().subscribe();
                    view.removeUser();
                }, throwable -> Utils.showError(view, throwable));
    }

}
