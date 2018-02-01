package com.example.andrey.newtmpclient.activities.createuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.createuser.di.CreateNewUserComponent;
import com.example.andrey.newtmpclient.activities.createuser.di.CreateNewUserModule;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.storage.SHAHashing;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.ButterKnife;

import static com.example.andrey.newtmpclient.storage.Const.FILL_FIELD;
import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;

public class CreateNewUserActivity extends BaseActivity implements CreateNewUserView {
    private static final String TAG = CreateNewUserActivity.class.getSimpleName();
    @Inject
    CreateNewUserPresenter presenter;
    EditText login;
    EditText password;
    EditText fio;
    AppCompatSpinner role;
    EditText phone;
    EditText email;
    Button create;
    String[] roles;
    String roleName;
    boolean isChecked = false;
    SHAHashing hashing = new SHAHashing();
    @BindString(R.string.make_new_user)String makeNewUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        initBackButton();
        ((CreateNewUserComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new CreateNewUserModule(this))).inject(this);
        ButterKnife.bind(this);
        setDialogTitleAndText("Создание пользователя", PLEASE_WAIT);
        changeToolbarTitle(makeNewUser);
        init();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adapter);
        role.setSelection(0);
        btnClick();
        create.setOnClickListener(v -> {
            createUser();
        });
    }

    private void checkFieldsFull(){
        if(login.getText().toString().equals("")){
            login.setHint("");
            login.setHint(FILL_FIELD);
        }
        else if(password.getText().toString().equals("")){
            password.setText("");
            password.setHint(FILL_FIELD);
        }
        else if(fio.getText().toString().equals("")){
            fio.setText("");
            fio.setHint(FILL_FIELD);
        }
        else if(phone.getText().toString().equals("")){
            phone.setText("");
            phone.setHint(FILL_FIELD);
        }
        else if(email.getText().toString().equals("")){
            email.setText("");
            email.setHint(FILL_FIELD);
        }else
            isChecked=true;
    }

    private void init(){
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        fio = findViewById(R.id.fio);
        create = findViewById(R.id.create);
        role = findViewById(R.id.spinner_roles);
        roles  = new String[]{UserRole.USER_ROLE, UserRole.MANAGER_ROLE, UserRole.ADMIN_ROLE};
    }

    private void btnClick() {
        role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roleName = roles[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                roleName = roles[2];
            }
        });
    }


    private void createUser(){
        checkFieldsFull();

        if(isChecked) {
            User user = new User(
                    login.getText().toString(),
                    hashing.hashPwd(password.getText().toString()),
                    fio.getText().toString(),
                    roleName,
                    phone.getText().toString(),
                    email.getText().toString()
            );
//            converter.sendMessage(new Request(user, Request.ADD_NEW_USER));
            login.setText("");
            password.setText("");
            phone.setText("");
            email.setText("");
            fio.setText("");
            presenter.createUser(user);
//            Intent intent = new Intent(this, MainTmpActivity.class)
//                    .putExtra("newUser", true);
//            new Updater(this, new Request(user, Request.ADD_NEW_USER), intent).execute();
//            startActivityWithComment(new Intent(CreateUserActivity.this, UsersActivity.class).putExtra("newUser", true));
        }
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, MainTmpActivity.class)
                .putExtra("newUser", true);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            App.getComponentManager().releaseComponent(getClass());
        }
    }

}
