package com.example.andrey.newtmpclient.activities.maindrawer;

import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.CommentsManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.TokenManager;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.rx.TransformerDialog;
import com.example.andrey.newtmpclient.utils.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainTmpPresenter {
    private static final String TAG = MainTmpPresenter.class.getSimpleName();
    private MainTmpView view;
    private MainTmpInterActor interActor;

    public MainTmpPresenter(MainTmpView view, MainTmpInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void logout(){
        interActor.logout()
                .compose(new TransformerDialog<>(view))
                .subscribe(response -> {
                    logoutAll();
                }, throwable -> Utils.showError(view, throwable));
    }

    private void logoutAll(){
        Client.INSTANCE.setAuth(false);
        allClear();
        view.stopServices();
//        super.onPostExecute(aVoid);
//        dialog.dismiss();
        UsersManager.INSTANCE.setUser(null);
        TokenManager.instance.setToken(null);
    }

    private void allClear(){
        TasksManager.INSTANCE.removeAll();
        AddressManager.INSTANCE.removeAll();
        CommentsManager.INSTANCE.removeAll();
        UserRolesManager.INSTANCE.removeAll();
        UsersManager.INSTANCE.removeAll();
    }
}
