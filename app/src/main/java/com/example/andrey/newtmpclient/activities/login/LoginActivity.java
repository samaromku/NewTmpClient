package com.example.andrey.newtmpclient.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.login.di.LoginComponent;
import com.example.andrey.newtmpclient.activities.login.di.LoginModule;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.service.GpsService;
import com.example.andrey.newtmpclient.utils.Const;

import javax.inject.Inject;

import static com.example.andrey.newtmpclient.storage.Const.AUTH;
import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;

/**
 * Created by andrey on 13.07.2017.
 */

public class LoginActivity extends BaseActivity implements LoginView {
    private EditText writeName;
    private EditText writePwd;
    @Inject LoginPresenterImpl loginPresenter;
    private String login;
    private String pwd;
    public static final String TAG = "LoginActivity";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ((LoginComponent)App.getComponentManager().getPresenterComponent(getClass(), new LoginModule(this))).inject(this);
        loginPresenter.init();
        Button signIn = (Button) findViewById(R.id.sign_in);
        CheckBox isInside = (CheckBox) findViewById(R.id.inside_ip_checkbox);
        writeName = (EditText) findViewById(R.id.write_name);
        writePwd = (EditText) findViewById(R.id.write_pwd);
        changeToolbarTitle(AUTH);
        setDialogTitleAndText(AUTH, PLEASE_WAIT);
        signIn.setOnClickListener(v -> loginPresenter.singIn(writeName.getText().toString(), writePwd.getText().toString()));
        isInside.setOnCheckedChangeListener((buttonView, isChecked) -> loginPresenter.setChecked(isChecked));
        writePwd.setText(pwd);
        writeName.setText(login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginPresenter.startAccountActivityAfterCheck();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isFinishing()){
            App.getComponentManager().releaseComponent(getClass());
        }
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, MainTmpActivity.class));
//        Intent intent = new Intent(this, MainTmpActivity.class)
//                .putExtra(Const.FROM_AUTH, true);
//        startActivity(intent);
    }

    @Override
    public void makeAuthResponse(User user) {
        Intent intent = new Intent(this, MainTmpActivity.class)
                .putExtra(Const.FROM_AUTH, true);
        new UpdateAuth(this, new Request(user, Request.AUTH), intent).execute();
    }

    @Override
    public void successAuth() {
        Intent intent = new Intent(this, MainTmpActivity.class);
        startService(GpsService.newIntent(this));
        GpsService.setServiceAlarm(this, true);
        startActivity(intent);
//        AuthChecker.checkServerErrorRedirectLoginActivity(this);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
