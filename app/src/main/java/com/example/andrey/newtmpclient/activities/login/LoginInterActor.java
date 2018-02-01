package com.example.andrey.newtmpclient.activities.login;

import android.util.Log;

import com.example.andrey.newtmpclient.di.ChangeUrlInterceptor;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.CommentsManager;
import com.example.andrey.newtmpclient.managers.ContactsManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.TokenManager;
import com.example.andrey.newtmpclient.managers.UserCoordsManager;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.CheckNetwork;
import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;
import com.example.andrey.newtmpclient.storage.Prefs;
import com.example.andrey.newtmpclient.storage.SHAHashing;

import io.reactivex.Observable;

import static com.example.andrey.newtmpclient.network.Response.ADD_ACTION_ADMIN;
import static com.example.andrey.newtmpclient.network.Response.ADD_TASKS_TO_USER;

/**
 * Created by andrey on 13.07.2017.
 */

public class LoginInterActor  {
    private SHAHashing hashing = new SHAHashing();
    private CheckNetwork checkNetwork = CheckNetwork.instance;
    private TmpService tmpService;
    private UserRolesManager userRolesManager = UserRolesManager.INSTANCE;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private AddressManager addressManager = AddressManager.INSTANCE;
    private Client client = Client.INSTANCE;
    private TokenManager tokenManager = TokenManager.instance;
    public static final String TAG = "LoginInterActor";
    private ChangeUrlInterceptor interceptor;

    public LoginInterActor(TmpService tmpService, ChangeUrlInterceptor interceptor) {
        this.tmpService = tmpService;
        this.interceptor = interceptor;
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

    int getFragmentCount(){
        return Client.INSTANCE.getFragmentCount();
    }

    void checkNetwork(boolean isChecked) {
        interceptor.setInner(isChecked);
        checkNetwork.setNetworkInsideOrOutside(isChecked);
    }

    Observable<Response> makeAuthResponse(User user){
        return tmpService.login(new Request(user, Request.AUTH))
                .doOnNext(response -> {
                    if(response.getResponse().equals(ADD_ACTION_ADMIN)){
                        client.setAuth(true);
                        Log.i("data", "добавляем админа");
                        tasksManager.addUnique(response.getTaskList());
                        usersManager.addAll(response.getUserList());
                        tokenManager.setToken(response.getToken());
                        Log.i(TAG, "token "+ tokenManager.getToken());
                        for (User u : usersManager.getUsers()) {
                            userRolesManager.addUserRole(u.getUserRole());
                            if (u.getLogin().equals(Prefs.getUser().getLogin())) {
                                usersManager.setUser(u);
                            }
                        }

                        User userFromResponse = usersManager.getUser();
                        userRolesManager.setUserRole(userRolesManager.getRoleByUserId(userFromResponse.getId()));
                        Log.i(TAG, "workWithData: " + userRolesManager.getUserRole().isMakeTasks());
                    }else if(response.getResponse().equals(ADD_TASKS_TO_USER)){
                        client.setAuth(true);
                        tasksManager.addUnique(response.getTaskList());
                        //tasksManager.removeDone();
                        usersManager.setUser(response.getUser());
                        usersManager.addAll(response.getUserList());
                        userRolesManager.addUserRole(response.getUser().getUserRole());
                        addressManager.addAllUserAddresses(response.getAddresses());
                        userRolesManager.setUserRole(userRolesManager.getRoleByUserId(response.getUser().getId()));
                        tokenManager.setToken(response.getToken());
                        Log.i(TAG, "token "+ tokenManager.getToken());
                    }else {
                        client.setAuth(false);
                    }
                });
    }
}
