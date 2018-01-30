package com.example.andrey.newtmpclient.activities.taskactivity;

import com.example.andrey.newtmpclient.base.basemvp.BaseView;

import java.util.List;

public interface OneTaskView extends BaseView{

    void startMainActivity(String extra);

    void startUpdateActivity();
}
