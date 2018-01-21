package com.example.andrey.newtmpclient.login;

import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.network.CheckNetwork;
import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.network.TmpService;
import com.example.andrey.newtmpclient.storage.Prefs;
import com.example.andrey.newtmpclient.storage.SHAHashing;

import io.reactivex.Observable;

/**
 * Created by andrey on 13.07.2017.
 */

public class LoginInterActor  {
    private SHAHashing hashing = new SHAHashing();
    private CheckNetwork checkNetwork = CheckNetwork.instance;
    private TmpService tmpService;

    public LoginInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<User>init(){
        return Observable.just(Prefs.getUser());
    }

    Observable<User> checkUser(String login, String pwd) {
        Prefs.addUser(login, pwd);
        User user = new User(login, hashing.hashPwd(pwd));
        return Observable.just(user);
    }

    boolean checkAuth() {
        return Client.INSTANCE.isAuth();
    }

    void checkNetwork(boolean isChecked) {
        checkNetwork.setNetworkInsideOrOutside(isChecked);
    }

}
