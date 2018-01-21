package com.example.andrey.newtmpclient.activities.maindrawer;

import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.CommentsManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.TokenManager;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Client;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.andrey.newtmpclient.storage.Const.SUCCESS_LOGOUT;

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(response.getResponse().equals(SUCCESS_LOGOUT)){
                        logoutAll();
                    }else {
                        view.showToast("Вы и так не залогинены");
                    }
                }, Throwable::printStackTrace);
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
