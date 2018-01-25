package com.example.andrey.newtmpclient.activities.userrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.activities.userrole.di.NewUserRoleComponent;
import com.example.andrey.newtmpclient.activities.userrole.di.NewUserRoleModule;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.managers.UserRolesManager;

import javax.inject.Inject;

import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;

public class NewUserRoleActivity extends BaseActivity implements NewUserRoleView {
    private static final String TAG = NewUserRoleActivity.class.getSimpleName();
    @Inject
    NewUserRolePresenter presenter;
    private int userId;
    private CheckBox makeUser;
    private CheckBox makeTask;
    private CheckBox correctionTask;
    private CheckBox makeAddress;
    private CheckBox watchAddress;
    private CheckBox correctionStatus;
    private CheckBox makeExecutor;
    private CheckBox correctionExecutor;
    private CheckBox watchTasks;
    private CheckBox commentTasks;
    private CheckBox changePassword;
    private Button send;
    private UserRole userRole;
    UserRolesManager userRolesManager = UserRolesManager.INSTANCE;
    private boolean hasRight = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_role_activity);
        ((NewUserRoleComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new NewUserRoleModule(this))).inject(this);
        changeToolbarTitle("Права");
        setDialogTitleAndText("Изменение прав", PLEASE_WAIT);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        init();
        send.setOnClickListener(v -> {
            addOrChangeUserRoleOnServer();
//            startActivityWithComment(new Intent(this, UsersActivity.class));
        });
    }

    private void addOrChangeUserRoleOnServer(){
        UserRole userRole1 = new UserRole(
                userId,
                makeUser.isChecked(),
                makeTask.isChecked(),
                correctionTask.isChecked(),
                makeAddress.isChecked(),
                watchAddress.isChecked(),
                correctionStatus.isChecked(),
                makeExecutor.isChecked(),
                correctionExecutor.isChecked(),
                watchTasks.isChecked(),
                commentTasks.isChecked(),
                changePassword.isChecked(),
                userId);
        userRolesManager.setUpdateUserRole(userRole1);
        presenter.updateUserRole(userRole1);
//        Intent intent = new Intent(this, MainTmpActivity.class);
//        new Updater(this, new Request(userRole1, Request.CHANGE_PERMISSION_PLEASE), intent).execute();
//        converter.sendMessage(new Request(userRole1, Request.CHANGE_PERMISSION_PLEASE));
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, MainTmpActivity.class);
        startActivity(intent);
    }

    private void init(){
        send = (Button) findViewById(R.id.send);
        makeUser = (CheckBox) findViewById(R.id.make_user);
        makeTask = (CheckBox) findViewById(R.id.make_task);
        correctionTask = (CheckBox) findViewById(R.id.correction_task);
        makeAddress = (CheckBox) findViewById(R.id.make_address);
        watchAddress = (CheckBox) findViewById(R.id.watch_address);
        correctionStatus = (CheckBox) findViewById(R.id.correction_status);
        makeExecutor = (CheckBox) findViewById(R.id.make_executor);
        correctionExecutor = (CheckBox) findViewById(R.id.correction_executor);
        watchTasks = (CheckBox) findViewById(R.id.watch_tasks);
        commentTasks = (CheckBox) findViewById(R.id.comment_tasks);
        changePassword = (CheckBox) findViewById(R.id.change_password);

        userRole = userRolesManager.getRoleByUserId(userId);
        noRight();
        if(hasRight) {
            makeUser.setChecked(userRole.isMakeNewUser());
            makeTask.setChecked(userRole.isMakeTasks());
            correctionTask.setChecked(userRole.isCorrectionTask());
            makeAddress.setChecked(userRole.isMakeAddress());
            watchAddress.setChecked(userRole.isWatchAddress());
            correctionStatus.setChecked(userRole.isCorrectionStatus());
            makeExecutor.setChecked(userRole.isMakeExecutor());
            correctionExecutor.setChecked(userRole.isCorrectionExecutor());
            watchTasks.setChecked(userRole.isWatchTasks());
            commentTasks.setChecked(userRole.isCommentTasks());
            changePassword.setChecked(userRole.isChangePassword());
        }
    }

    private void noRight(){
        if(userRole==null){
            Toast.makeText(this, "вы не имеете права", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainTmpActivity.class));
        }else hasRight = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            App.getComponentManager().releaseComponent(getClass());
        }
    }



}
