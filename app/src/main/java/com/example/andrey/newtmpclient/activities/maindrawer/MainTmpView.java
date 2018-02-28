package com.example.andrey.newtmpclient.activities.maindrawer;

import com.example.andrey.newtmpclient.base.basemvp.BaseView;

public interface MainTmpView extends BaseView{

    void showToast(String text);

    void stopServices();
}
