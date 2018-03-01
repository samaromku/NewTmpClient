package com.example.andrey.newtmpclient.activities.oneuser;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.activities.oneuser.di.OneUserComponent;
import com.example.andrey.newtmpclient.activities.oneuser.di.OneUserModule;
import com.example.andrey.newtmpclient.activities.userrole.NewUserRoleActivity;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.utils.Utils;

import javax.inject.Inject;

import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;

public class OneUserActivity extends BaseActivity implements OneUserView {
    private static final String TAG = OneUserActivity.class.getSimpleName();
    @Inject
    OneUserPresenter presenter;
    private User user;
    private ImageView change;
    UsersManager usersManager = UsersManager.INSTANCE;
    UserRolesManager userRolesManager = UserRolesManager.INSTANCE;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ((OneUserComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new OneUserModule(this))).inject(this);
        init();
        setDialogTitleAndText("Удаление пользователя", PLEASE_WAIT);
        initBackButton();
        changeToolbarTitle(user.getLogin());
        change.setOnClickListener(v -> {
            if (userRolesManager.getRoleByUserId(user.getId()) != null) {
                startActivity(new Intent(this, NewUserRoleActivity.class)
                        .putExtra("userId", user.getId()));
                finish();
            } else {
                Toast.makeText(this, "вы не имеете права", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void removeUser() {
        Intent intent = new Intent(this, MainTmpActivity.class)
                .putExtra("removeUser", true);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            App.getComponentManager().releaseComponent(getClass());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (userRolesManager.getUserRole().isMakeNewUser()) {
            getMenuInflater().inflate(R.menu.user_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.remove_user:
                Utils.showDialog(this,
                        "Удаление пользователя",
                        "Вы действительно хотите удалить пользователя? Действие необратимо!",
                        (dialogInterface, i) -> presenter.removeUser(user));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
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
