package com.example.andrey.newtmpclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.Updater;

public class UserActivity extends BaseActivity{
    private User user;
    private ImageView change;
    UsersManager usersManager = UsersManager.INSTANCE;
    UserRolesManager userRolesManager = UserRolesManager.INSTANCE;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        init();
        initBackButton();
        changeToolbarTitle(user.getLogin());
        change.setOnClickListener(v -> {
            startActivity(new Intent(UserActivity.this, UserRoleActivity.class)
                    .putExtra("userId", user.getId()));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(UserActivity.this, UsersActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(userRolesManager.getUserRole().isMakeNewUser()) {
            getMenuInflater().inflate(R.menu.user_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.remove_user:
                Intent intent = new Intent(this, MainTmpActivity.class).putExtra("removeUser", true);
                usersManager.setRemoveUser(user);
                new Updater(this, new Request(user, Request.REMOVE_USER), intent).execute();
//                converter.sendMessage(new Request(user, Request.REMOVE_USER));

//                startActivityWithComment(new Intent(this, UsersActivity.class).putExtra("removeUser", true));
                return true;

            default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void init(){
        change = (ImageView) findViewById(R.id.change);
        TextView login = (TextView) findViewById(R.id.login);
        TextView fio = (TextView) findViewById(R.id.fio);
        TextView role = (TextView) findViewById(R.id.role);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView email = (TextView) findViewById(R.id.email);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        user = usersManager.getUsers().get(position);

        login.setText(user.getLogin());
        fio.setText(user.getFIO());
        role.setText(user.getRole());
        phone.setText(user.getTelephone());
        email.setText(user.getEmail());
    }
}
