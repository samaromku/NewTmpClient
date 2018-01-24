package com.example.andrey.newtmpclient.activities.allusers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.MapActivity;
import com.example.andrey.newtmpclient.activities.UsersActivity;
import com.example.andrey.newtmpclient.activities.allusers.di.AllUsersComponent;
import com.example.andrey.newtmpclient.activities.allusers.di.AllUsersModule;
import com.example.andrey.newtmpclient.activities.createuser.CreateNewUserActivity;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.activities.oneuser.OneUserActivity;
import com.example.andrey.newtmpclient.adapter.UserAdapter;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;
import com.example.andrey.newtmpclient.storage.Updater;

import javax.inject.Inject;

public class AllUsersActivity extends BaseActivity implements AllUsersView {
    private static final String TAG = AllUsersActivity.class.getSimpleName();
    @Inject
    AllUsersPresenter presenter;
    FloatingActionButton addUser;
    RecyclerView userList;
    UserAdapter adapter;
    UsersManager usersManager = UsersManager.INSTANCE;
    UserRolesManager userRolesManager = UserRolesManager.INSTANCE;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_users);
        changeToolbarTitle("Пользователи");
        ((AllUsersComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new AllUsersModule(this))).inject(this);
        init();
        adminAction();

        userList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(usersManager.getUsers(), clickListener);
        userList.setAdapter(adapter);
    }

    private OnListItemClickListener clickListener = (v, position) -> {
        startActivity(new Intent(this, OneUserActivity.class).putExtra("position", position));
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainTmpActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.users_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                Intent intent = new Intent(this, MapActivity.class);
                new Updater(this, new Request(Request.GIVE_ME_LAST_USERS_COORDS), intent).execute();
//            converter.sendMessage(new Request(Request.GIVE_ME_LAST_USERS_COORDS));
//            startActivityWithComment(new Intent(this, MapActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void adminAction(){
        UserRole userRole = userRolesManager.getUserRole();
        //настройка видимости кнопки
        if(userRole!=null && userRole.isMakeNewUser()){
            addUser.setVisibility(View.VISIBLE);
        }else addUser.setVisibility(View.INVISIBLE);
        addUser.setOnClickListener(v -> startActivity(
                new Intent(this, CreateNewUserActivity.class)));
    }

    private void init(){
        addUser = (FloatingActionButton) findViewById(R.id.add_user);
        userList = (RecyclerView) findViewById(R.id.users);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            App.getComponentManager().releaseComponent(getClass());
        }
    }

}
