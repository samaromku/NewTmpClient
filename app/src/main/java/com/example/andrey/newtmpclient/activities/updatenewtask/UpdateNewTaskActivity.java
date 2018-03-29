package com.example.andrey.newtmpclient.activities.updatenewtask;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.activities.updatenewtask.di.UpdateNewTaskComponent;
import com.example.andrey.newtmpclient.activities.updatenewtask.di.UpdateNewTaskModule;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.fragments.datepicker.DatePickerFragment;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.storage.DateUtil;
import com.example.andrey.newtmpclient.utils.Utils;

import javax.inject.Inject;

import static com.example.andrey.newtmpclient.fragments.one_task_fragment.OneTaskPageFragment.VOICE_RECOGNITION_REQUEST_CODE;
import static com.example.andrey.newtmpclient.storage.Const.FILL_FIELD;
import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;

public class UpdateNewTaskActivity extends BaseActivity implements UpdateNewTaskView {
    private static final String TAG = UpdateNewTaskActivity.class.getSimpleName();
    @Inject
    UpdateNewTaskPresenter presenter;
    private AppCompatSpinner importanceSpinner;
    private AppCompatSpinner statusSpinner;
    private EditText body;
    private Button chooseDate;
    private AutoCompleteTextView addressesForUpdate;
    private AppCompatSpinner userSpinner;
    private AppCompatSpinner typeSpinner;
    private FloatingActionButton createTask;
    private String userName;
    private DateUtil dateUtil = new DateUtil();
    private AddressManager addressManager = AddressManager.INSTANCE;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private String[] addresses;
    private String[] importance = tasksManager.getImportanceString();
    private String[] userNames;
    private String[] statuses = tasksManager.getAllStatuses();
    private String[] types = tasksManager.getType();
    private String importanceSelected;
    private String typeSelected;
    private String statusSelected;
    private int taskId;
    private static final String DIALOG_DATE = "date_dialog";
    private int newUserPosition = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        initBackButton();
        ((UpdateNewTaskComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new UpdateNewTaskModule(this))).inject(this);
        setDialogTitleAndText("Обновляение задания", PLEASE_WAIT);
        changeToolbarTitle("Изменить задание");
        init();
        getFromIntent();
        clickOnUserSpinner();
        clickOnImportance();
        clickOnType();
        clickOnStatus();
        setNeedData();

        createTask.setOnClickListener(v -> clickOnBtnCreateTask());
        chooseDate.setOnClickListener(v -> chooseDate());
        ImageButton btnMic = findViewById(R.id.btnMic);
        btnMic.setOnClickListener(v -> Utils.startInputVoice(this));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            body.append(" " + data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
            Log.i(TAG, "onActivityResult: " + data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void clickOnBtnCreateTask(){
        if (addressesForUpdate.getText().toString().equals("")) {
            addressesForUpdate.setHint(FILL_FIELD);
        } else if (body.getText().toString().equals("")) {
            body.setHint(FILL_FIELD);
        } else if (chooseDate.getText().toString().equals("Выбрать дату")) {
            chooseDate.setText(FILL_FIELD);
        } else {
            Address address = addressManager.getAddressByAddress(addressesForUpdate.getText().toString());
            Task task = new Task.Builder()
                    .id(tasksManager.getMaxId() + 1)
                    .created(dateUtil.currentDate())
                    .importance(importanceSelected)
                    .body(body.getText().toString())
                    .status(statusSelected)
                    .type(typeSelected)
                    .doneTime(chooseDate.getText().toString())
                    .userId(usersManager.getUserByUserName(userName).getId())
                    .addressId(address.getId())
                    .build();

            task.setAddress(address.getAddress());
            task.setOrgName(address.getName());
            tasksManager.setTask(task);
            task.setId(taskId);

            if(address.getId()==0){
                Toast.makeText(this, "Список адресов пуст, получите его", Toast.LENGTH_SHORT).show();
            }else {
                presenter.updateTask(task);
//                Intent intent = new Intent(this, MainTmpActivity.class).putExtra("createTask", true);
//                new Updater(this, new Request(task, Request.UPDATE_TASK), intent).execute();
            }
        }
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, MainTmpActivity.class)
                .putExtra("createTask", true);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void chooseDate(){
        FragmentManager manager = getSupportFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(dateUtil.getDate());
        dialog.show(manager, DIALOG_DATE);
    }

    private void setNeedData(){

        statuses = tasksManager.getAllStatuses();
        Task task = tasksManager.getById(taskId);
        body.setText(task.getBody());
        chooseDate.setText(task.getDoneTime());
//        createTask.setText("Изменить задание");
        addressesForUpdate.setText(task.getAddress());
        int userPosition = 0;
        //заглушка на удаленного пользователя
        if(usersManager.getUserById(task.getUserId())!=null) {
            for (int i = 0; i < userNames.length; i++) {
                if (userNames[i].equals(usersManager.getUserById(task.getUserId()).getLogin()))
                    userPosition = i;
            }
        }
        userSpinner.setSelection(userPosition);
        int importancePostition = 0;
        for (int i = 0; i < importance.length; i++) {
            if(importance[i].equals(task.getImportance()))
                importancePostition = i;
        }
        importanceSpinner.setSelection(importancePostition);
        int statusPosition = 0;
        for (int i = 0; i < statuses.length; i++) {
            if(statuses[i].equals(task.getStatus()))
                statusPosition = i;
        }
        System.out.println(statusPosition+"status");
        statusSpinner.setSelection(statusPosition);
    }

    private void getFromIntent(){
        Intent intent = getIntent();
        taskId = intent.getIntExtra("taskId", 0);
    }

    private void init(){
        chooseDate = findViewById(R.id.choose_date);
        addressesForUpdate = findViewById(R.id.task_title);
        importanceSpinner = findViewById(R.id.spinner_importance);
        statusSpinner = findViewById(R.id.spinner_status);
        typeSpinner = findViewById(R.id.spinner_type);
        body = findViewById(R.id.task_body);
        userSpinner = findViewById(R.id.spinner_users);
        createTask = findViewById(R.id.create_task_btn);
        createOrgNames();
        createDropMenuOrgNames();
        createUserNames();
        getImportance();
    }


    private void createUserNames(){
        userNames = new String[usersManager.getUsers().size()];
        for (int i = 0; i < usersManager.getUsers().size(); i++) {
            userNames[i] = usersManager.getUsers().get(i).getLogin();
        }
    }

    private void getImportance(){
        importanceSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tasksManager.getImportanceString()));
    }

    private void createOrgNames(){
        addresses = new String[addressManager.getAddresses().size()];
        for (int i = 0; i < addressManager.getAddresses().size(); i++) {
            addresses[i] = addressManager.getAddresses().get(i).getAddress();
        }
    }


    private void createDropMenuOrgNames(){
        addressesForUpdate.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, addresses));
        if(addresses.length==0){
            addressesForUpdate.setHint("Получите адреса в нужной вкладке");
        }
    }

    private void clickOnStatus(){
        baseSpinner(statuses,
                statusSpinner,
                position -> {
                    statusSelected = statuses[position];
                    if(statuses[position].equals(TaskEnum.NEW_TASK)){
                        for (int i = 0; i < userNames.length; i++) {
                            if(userNames[i].equals("Не назначена")){
                                newUserPosition = i;
                            }
                        }
                        userSpinner.setSelection(newUserPosition);
                    }
                }, () -> statusSelected = statuses[0]);
    }

    private void clickOnType(){
        baseSpinner(types,
                typeSpinner,
                position -> typeSelected = types[position],
                () -> typeSelected = types[0]);
    }

    private void clickOnUserSpinner(){
        baseSpinner(userNames,
                userSpinner,
                position -> {
                    userName = userNames[position];
                    if(position==newUserPosition){
                        statusSpinner.setSelection(0);
                    }else{
                        statusSpinner.setSelection(1);
                    }
                }, () -> userName = userNames[0]);
    }


    private void clickOnImportance(){
        baseSpinner(importance,
                importanceSpinner,
                position -> importanceSelected = importance[position],
                () -> importanceSelected = importance[0]);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            App.getComponentManager().releaseComponent(getClass());
        }
    }

}
