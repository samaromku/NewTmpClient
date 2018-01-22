package com.example.andrey.newtmpclient.createTask;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.fragments.address.AddressMvpFragment;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.createTask.di.CreateTaskComponent;
import com.example.andrey.newtmpclient.createTask.di.CreateTaskModule;

import javax.inject.Inject;

import static com.example.andrey.newtmpclient.storage.Const.CREATING_TASK;
import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;

public class CreateTaskActivity extends BaseActivity implements CreateTaskView {
    private AppCompatSpinner importanceSpinner;
    private AppCompatSpinner statusSpinner;
    private EditText body;
    private Button chooseDate;
    private AutoCompleteTextView anndressNamesCompleteTV;
    private AppCompatSpinner userSpinner;
    private AppCompatSpinner typeSpinner;
    private Button createTask;
    private ProgressDialog mDialog;
    @Inject CreateTaskPresenterImpl createTaskPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        changeToolbarTitle(R.string.create_task);
        chooseDate = (Button) findViewById(R.id.choose_date);
        anndressNamesCompleteTV = (AutoCompleteTextView) findViewById(R.id.task_title);
        importanceSpinner = (AppCompatSpinner) findViewById(R.id.spinner_importance);
        statusSpinner = (AppCompatSpinner) findViewById(R.id.spinner_status);
        typeSpinner = (AppCompatSpinner) findViewById(R.id.spinner_type);
        body = (EditText) findViewById(R.id.task_body);
        userSpinner = (AppCompatSpinner) findViewById(R.id.spinner_users);
        createTask = (Button) findViewById(R.id.create_task_btn);
        ((CreateTaskComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new CreateTaskModule(this))).inject(this);
        initPresenter();
        mDialog = new ProgressDialog(this);
        mDialog.setCancelable(false);
        mDialog.setMessage(CREATING_TASK);
        mDialog.setTitle(PLEASE_WAIT);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {
        mDialog.show();
    }

    @Override
    public void hideDialog() {
        mDialog.hide();
    }

    private void initPresenter() {
        createTaskPresenter.setContext(this);
        createTask.setOnClickListener(v -> createTaskPresenter.checkValidFields());
        chooseDate.setOnClickListener(v -> createTaskPresenter.chooseDate(getSupportFragmentManager()));
        createTaskPresenter.clickOnImportance(importanceSpinner);
        createTaskPresenter.clickOnStatuses(statusSpinner);
        createTaskPresenter.clickOnTypes(typeSpinner);
        createTaskPresenter.clickOnUserNames(userSpinner);
        createTaskPresenter.setAddressNameAdapter(anndressNamesCompleteTV);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getComponentManager().releaseComponent(getClass());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public String getAddress() {
        return anndressNamesCompleteTV.getText().toString();
    }

    @Override
    public String getImportance() {
        return importanceSpinner.getSelectedItem().toString();
    }

    @Override
    public String getType() {
        return typeSpinner.getSelectedItem().toString();
    }

    @Override
    public String getStatus() {
        return statusSpinner.getSelectedItem().toString();
    }

    @Override
    public String getUser() {
        return userSpinner.getSelectedItem().toString();
    }

    public void setAnndressNamesCompleteTV(String text) {
        anndressNamesCompleteTV.setHint(text);
    }

    @Override
    public void setBodyText(String text) {
        body.setHint(text);
    }

    @Override
    public String getBody() {
        return body.getText().toString();
    }

    @Override
    public String getDate() {
        return chooseDate.getText().toString();
    }

    @Override
    public void setStatusSpinnerSelection(int position) {
        statusSpinner.setSelection(position);
    }

    @Override
    public void setUserSelection(int position) {
        userSpinner.setSelection(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void finishCreateActivity() {
        finish();
    }
}
