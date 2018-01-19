package com.example.andrey.newtmpclient.base;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.andrey.newtmpclient.interfaces.OnChangeTitle;


/**
 * Created by Andrey on 25.09.2017.
 */

public class BaseFragment extends Fragment{
    protected OnChangeTitle onChangeTitle;

    public void setOnChangeTitle(OnChangeTitle onChangeTitle) {
        this.onChangeTitle = onChangeTitle;
    }

    protected void setToolbarTitle(String title){
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle(title);
        }
    }
}
