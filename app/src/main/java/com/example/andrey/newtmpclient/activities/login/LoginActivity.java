package com.example.andrey.newtmpclient.activities.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
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
import com.example.andrey.newtmpclient.service.GpsService;

import javax.inject.Inject;

import io.victoralbertos.rx2_permissions_result.RxPermissionsResult;

import static android.os.Build.VERSION_CODES.LOLLIPOP_MR1;
import static com.example.andrey.newtmpclient.storage.Const.AUTH;
import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;
import static com.example.andrey.newtmpclient.storage.Const.STATE_COUNT;

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
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ((LoginComponent)App.getComponentManager().getPresenterComponent(getClass(), new LoginModule(this))).inject(this);
        loginPresenter.init();
        Button signIn = findViewById(R.id.sign_in);
        CheckBox isInside = findViewById(R.id.inside_ip_checkbox);
        writeName = findViewById(R.id.write_name);
        writePwd = findViewById(R.id.write_pwd);
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
    public void startMainActivity(int stateFragment) {
        startActivity(new Intent(this, MainTmpActivity.class)
        .putExtra(STATE_COUNT, stateFragment));
    }

    @Override
    public void successAuth() {
        if(Build.VERSION.SDK_INT > LOLLIPOP_MR1){
            RxPermissionsResult.on(this).requestPermissions(permissions)
                    .subscribe(result ->
                            showPermissionStatus(result.grantResults()));
        }else {
            startMainActivity(0);
        }
    }

    void showPermissionStatus(int[] grantResults) {
        boolean granted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        if (granted) {
            startMainActivityService();
        } else {
            Toast.makeText(this, "Нужно разрешение на получение геопозиции", Toast.LENGTH_SHORT).show();
        }
    }

    private void startMainActivityService(){
        Intent intent = new Intent(this, MainTmpActivity.class);
        startService(GpsService.newIntent(this));
        GpsService.setServiceAlarm(this, true);
        startActivity(intent);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
