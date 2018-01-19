package com.example.andrey.newtmpclient.createTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.address.AddressMvpActivity;
import com.example.andrey.newtmpclient.createTask.presenter.CreateTaskPresenter;
import com.example.andrey.newtmpclient.createTask.presenter.CreateTaskPresenterImpl;
import com.example.andrey.newtmpclient.createTask.view.CreateTaskView;

public class CreateTaskActivity extends AppCompatActivity implements CreateTaskView {
    private AppCompatSpinner importanceSpinner;
    private AppCompatSpinner statusSpinner;
    private EditText body;
    private Button chooseDate;
    private AutoCompleteTextView anndressNamesCompleteTV;
    private AppCompatSpinner userSpinner;
    private AppCompatSpinner typeSpinner;
    private Button createTask;
    private CreateTaskPresenter createTaskPresenter;
    private ArrayAdapter<String> statusesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task_activity);
        getSupportActionBar().setTitle(R.string.create_task);
        chooseDate = (Button) findViewById(R.id.choose_date);
        anndressNamesCompleteTV = (AutoCompleteTextView) findViewById(R.id.task_title);
        importanceSpinner = (AppCompatSpinner) findViewById(R.id.spinner_importance);
        statusSpinner = (AppCompatSpinner) findViewById(R.id.spinner_status);
        typeSpinner = (AppCompatSpinner) findViewById(R.id.spinner_type);
        body = (EditText) findViewById(R.id.task_body);
        userSpinner = (AppCompatSpinner) findViewById(R.id.spinner_users);
        createTask = (Button) findViewById(R.id.create_task_btn);
        initPresenter();

    }

    private void initPresenter() {
        createTaskPresenter = new CreateTaskPresenterImpl(this, this);
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
        createTaskPresenter.onDestroy();
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
        createTaskPresenter.startAccountActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.address:
                getAddressesFromServer();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getAddressesFromServer() {
        Intent intent = new Intent(this, AddressMvpActivity.class);
        startActivity(intent);
    }

}
