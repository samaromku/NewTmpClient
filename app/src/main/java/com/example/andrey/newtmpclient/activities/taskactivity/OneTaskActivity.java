package com.example.andrey.newtmpclient.activities.taskactivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.login.LoginActivity;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.activities.needdoingtasks.NeedDoingTasksActivity;
import com.example.andrey.newtmpclient.activities.taskactivity.di.OneTaskComponent;
import com.example.andrey.newtmpclient.activities.taskactivity.di.OneTaskModule;
import com.example.andrey.newtmpclient.activities.updatenewtask.UpdateNewTaskActivity;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.managers.CommentsManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.utils.Utils;

import javax.inject.Inject;

import static com.example.andrey.newtmpclient.storage.Const.NOT_AUTH;
import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;
import static com.example.andrey.newtmpclient.storage.Const.TASK_NUMBER;

public class OneTaskActivity extends BaseActivity implements OneTaskView {
    private static final String TAG = OneTaskActivity.class.getSimpleName();
    @Inject
    OneTaskPresenter presenter;
    private int taskNumber;
    TasksManager tasksManager = TasksManager.INSTANCE;
    CommentsManager commentsManager = CommentsManager.INSTANCE;
    UsersManager usersManager = UsersManager.INSTANCE;
    UserRolesManager userRolesManager = UserRolesManager.INSTANCE;
    private Task task;
    boolean fromDoingList = false;
    public static final String FROM_DOING_LIST = "fromDoingList";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_task_view_pager);
        ((OneTaskComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new OneTaskModule(this))).inject(this);
        initBackButton();
        setDialogTitleAndText("Обработка запроса", PLEASE_WAIT);
        if(usersManager.getUser()!=null){
            taskNumber = getIntent()
                    .getIntExtra(TASK_NUMBER, 0);
            task = tasksManager.getById(taskNumber);
            if(getSupportActionBar()!=null)
                getSupportActionBar().setTitle(task.getStatus());
            ViewPager pager = findViewById(R.id.pager);
            PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), taskNumber);
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
                presenter.getAddresses();
                commentsManager.removeAll();
                return true;
            case R.id.remove_task:
                showRemoveDialog();
                return true;
            case R.id.check_as_done:
                clickToChangeStatus(TaskEnum.DONE_TASK);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showRemoveDialog(){
        Utils.showDialog(this,
                "Удаление заявки",
                "Вы действительно хотите удалить заявку? Действие необратимо!",
                (dialogInterface, i) -> presenter.removeTask(taskNumber));
    }

    @Override
    public void startMainActivity(String extra) {
        Intent intent = new Intent(this, MainTmpActivity.class)
                .putExtra(extra, true);
        startActivity(intent);
    }

    private void clickToChangeStatus(String changedStatusTask){
        presenter.changeTaskStatus(changedStatusTask, taskNumber);
    }

    @Override
    public void startUpdateActivity() {
        Intent intent = new Intent(this, UpdateNewTaskActivity.class)
                .putExtra("taskId", task.getId());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        fromDoingList = getIntent().getBooleanExtra(FROM_DOING_LIST, false);
        if(!fromDoingList) {
            super.onBackPressed();
            commentsManager.removeAll();
        }else {
            startActivity(new Intent(this, NeedDoingTasksActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            App.getComponentManager().releaseComponent(getClass());
        }
    }

}
