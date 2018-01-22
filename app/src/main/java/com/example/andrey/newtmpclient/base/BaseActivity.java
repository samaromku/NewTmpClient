package com.example.andrey.newtmpclient.base;

import android.app.ProgressDialog;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.base.basemvp.BaseView;
import com.example.andrey.newtmpclient.interfaces.OnNothingSelected;
import com.example.andrey.newtmpclient.interfaces.SpinnerListener;

import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;
import static com.example.andrey.newtmpclient.storage.Const.UPLOAD_FILE;


/**
 * Created by Andrey on 25.09.2017.
 */

public class BaseActivity extends AppCompatActivity implements BaseView{
    protected ProgressDialog dialog;

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
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void setDialogTitleAndText(String title, String message){
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage(message);
        dialog.setTitle(title);
    }

    protected void changeToolbarTitle(@StringRes int title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }

    protected void changeToolbarTitle(String title){
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle(title);
    }

    protected void initBackButton() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * @param stringArray       массив который скармливаем адаптеру
     * @param spinner           спиннер
     * @param spinnerListener   лямбду, которая отработает если выбрать итем
     * @param onNothingSelected лямбда, если ничего не выбрано
     */
    protected void baseSpinner(String[] stringArray,
                               AppCompatSpinner spinner,
                               SpinnerListener spinnerListener,
                               OnNothingSelected onNothingSelected) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, stringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerListener.onItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onNothingSelected.onNothingSelected();
            }
        });
    }
}
