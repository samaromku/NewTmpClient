package com.example.andrey.newtmpclient.base;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.andrey.newtmpclient.base.basemvp.BaseView;
import com.example.andrey.newtmpclient.interfaces.OnChangeTitle;


/**
 * Created by Andrey on 25.09.2017.
 */

public class BaseFragment extends Fragment implements BaseView{
    protected OnChangeTitle onChangeTitle;
    protected ProgressDialog dialog;

    public void setOnChangeTitle(OnChangeTitle onChangeTitle) {
        this.onChangeTitle = onChangeTitle;
    }

    protected void setToolbarTitle(String title){
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle(title);
        }
    }

    protected void setDialogTitleAndText(String title, String message){
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage(message);
        dialog.setTitle(title);
    }

    @Override
    public void showDialog() {
        dialog.show();
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
