package com.example.andrey.newtmpclient.activities.createTask;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.activities.createTask.di.CreateTaskComponent;
import com.example.andrey.newtmpclient.activities.createTask.di.CreateTaskModule;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.ButterKnife;

import static com.example.andrey.newtmpclient.storage.Const.CREATING_TASK;
import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;

public class CreateTaskActivity extends BaseActivity implements CreateTaskView {
    private AppCompatSpinner statusSpinner;
    private EditText body;
    private Button chooseDate;
    private AutoCompleteTextView anndressNamesCompleteTV;
    private AppCompatSpinner userSpinner;
    private Button createTask;
    private ProgressDialog mDialog;
    @Inject
    CreateTaskPresenter presenter;
    @BindString(R.string.create_task)String createTaskTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        ButterKnife.bind(this);
        changeToolbarTitle(createTaskTitle);
        chooseDate = (Button) findViewById(R.id.choose_date);
        anndressNamesCompleteTV = (AutoCompleteTextView) findViewById(R.id.task_title);
        statusSpinner = (AppCompatSpinner) findViewById(R.id.spinner_status);

        setDialogTitleAndText(createTaskTitle, PLEASE_WAIT);
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
        mDialog.dismiss();
    }

    private void initPresenter() {
        createTask.setOnClickListener(v -> presenter.checkValidFields());
        chooseDate.setOnClickListener(v -> presenter.chooseDate(getSupportFragmentManager()));
        presenter.clickOnImportance();
        presenter.clickOnStatuses();
        presenter.clickOnTypes();
        presenter.clickOnUserNames();
        presenter.setAddressNameAdapter();
    }

    @Override
    public void setImportance(String[] importance) {
        AppCompatSpinner importanceSpinner = (AppCompatSpinner) findViewById(R.id.spinner_importance);
        baseSpinner(importance, importanceSpinner,
                position -> presenter.setImportance(importance[position]),
                () -> presenter.setImportance(importance[0]));
    }

    @Override
    public void setTypes(String[] types) {
        AppCompatSpinner typeSpinner = (AppCompatSpinner) findViewById(R.id.spinner_type);
        baseSpinner(types, typeSpinner,
                position -> presenter.setType(types[position]),
                () -> presenter.setType(types[0]));
    }

    @Override
    public void setStatuses(String[] statuses) {
        baseSpinner(statuses, statusSpinner,
                position -> presenter.setSelectedStatusPosition(position, statuses),
                () -> presenter.setStatus(statuses[0]));
    }

    @Override
    public void setUserNames(String[] userNames) {
        baseSpinner(userNames, userSpinner,
                position -> {
                    if (position == 0) {
                        setStatusSpinnerSelection(0);
                    } else {
                        setStatusSpinnerSelection(1);
                    }
                    presenter.setUserName(userNames[position]);
                },
                () -> {
                    setStatusSpinnerSelection(0);
                    presenter.setUserName(userNames[0]);
                });
    }

    @Override
    public void setAddresses(String[] addresses) {
        anndressNamesCompleteTV.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.simple_dropdown_item_1line, addresses));
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
