package com.example.andrey.newtmpclient.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.utils.Const;

/**
 * Created by andrey on 13.07.2017.
 */

public class LoginActivity extends AppCompatActivity implements LoginView {
    private EditText writeName;
    private EditText writePwd;
    private LoginPresenterImpl loginPresenter;
    private String login;
    private String pwd;
    public static final String TAG = "LoginActivity";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        loginPresenter = new LoginPresenterImpl(this, this);
        Button signIn = (Button) findViewById(R.id.sign_in);
        CheckBox isInside = (CheckBox) findViewById(R.id.inside_ip_checkbox);
        writeName = (EditText) findViewById(R.id.write_name);
        writePwd = (EditText) findViewById(R.id.write_pwd);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Авторизация");
        }
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
        loginPresenter.onDestroy();
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
        Intent intent = new Intent(this, MainTmpActivity.class)
                .putExtra(Const.FROM_AUTH, true);
        startActivity(intent);
    }
}
