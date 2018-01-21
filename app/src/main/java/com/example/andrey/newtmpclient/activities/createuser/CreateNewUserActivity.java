package com.example.andrey.newtmpclient.activities.createuser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.createuser.di.CreateNewUserComponent;
import com.example.andrey.newtmpclient.activities.createuser.di.CreateNewUserModule;


import javax.inject.Inject;

public class CreateNewUserActivity extends AppCompatActivity implements CreateNewUserView {
    private static final String TAG = CreateNewUserActivity.class.getSimpleName();
    @Inject
    CreateNewUserPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);
        ((CreateNewUserComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new CreateNewUserModule(this))).inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            App.getComponentManager().releaseComponent(getClass());
        }
    }

}
