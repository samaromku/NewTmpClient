package com.example.andrey.newtmpclient.taskactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.RequestDoingActivity;
import com.example.andrey.newtmpclient.activities.UpdateTaskActivity;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.activities.login.LoginActivity;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.CommentsManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.Updater;

import static com.example.andrey.newtmpclient.storage.Const.NOT_AUTH;
import static com.example.andrey.newtmpclient.storage.Const.TASK_NUMBER;

public class TaskActivity extends BaseActivity{
    private int taskNumber;
    TasksManager tasksManager = TasksManager.INSTANCE;
    CommentsManager commentsManager = CommentsManager.INSTANCE;
    UsersManager usersManager = UsersManager.INSTANCE;
    UserRolesManager userRolesManager = UserRolesManager.INSTANCE;
    Task task;
    private AddressManager addressManager = AddressManager.INSTANCE;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    boolean fromDoingList = false;
    public static final String FROM_DOING_LIST = "fromDoingList";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_task_view_pager);
        initBackButton();
        if(usersManager.getUser()!=null){
        taskNumber = getIntent()
                .getIntExtra(TASK_NUMBER, 0);
        task = tasksManager.getById(taskNumber);
        if(getSupportActionBar()!=null)
        getSupportActionBar().setTitle(task.getStatus());
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), taskNumber);
        pager.setAdapter(pagerAdapter);

        }else {
            Toast.makeText(this, NOT_AUTH, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(userRolesManager.getUserRole()!=null) {
            if (userRolesManager.getUserRole().isCorrectionTask()) {
                getMenuInflater().inflate(R.menu.change_task_menu, menu);
            }
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_task:
                firstTimeAddAddresses();
                commentsManager.removeAll();
                return true;
            case R.id.remove_task:
                commentsManager.removeAll();
                tasksManager.setRemoveTask(task);
                Intent intent = new Intent(this, MainTmpActivity.class).putExtra("removeTask", true);
                new Updater(this, new Request(task, Request.REMOVE_TASK), intent).execute();
//                converter.sendMessage(new Request(task, Request.REMOVE_TASK));
//                startActivityWithComment(new Intent(this, AccountActivity.class).putExtra("removeTask", true));
                return true;
            case R.id.check_as_done:
                clickToChangeStatus(TaskEnum.DONE_TASK);
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void clickToChangeStatus(String changedStatusTask){
        task.setUserId(usersManager.getUser().getId());
        task.setStatus(changedStatusTask);
        tasksManager.setTask(task);
        commentsManager.removeAll();
        Intent intent = new Intent(this, MainTmpActivity.class).putExtra("statusChanged", true);
        new Updater(this, new Request(task, changedStatusTask), intent).execute();
    }


    private void firstTimeAddAddresses(){
        Intent intent = new Intent(this, UpdateTaskActivity.class).putExtra("taskId", task.getId());
        if(addressManager.getAddresses().size()==0) {
            new Updater(this, new Request(Request.GIVE_ME_ADDRESSES_PLEASE), intent).execute();
        }else startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        fromDoingList = getIntent().getBooleanExtra(FROM_DOING_LIST, false);
        if(!fromDoingList) {
            super.onBackPressed();
            startActivity(new Intent(this, MainTmpActivity.class));
            commentsManager.removeAll();
        }else {
            startActivity(new Intent(this, RequestDoingActivity.class));
        }
    }
}
