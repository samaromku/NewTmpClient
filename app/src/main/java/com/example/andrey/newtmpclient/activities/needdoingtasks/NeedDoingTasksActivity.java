package com.example.andrey.newtmpclient.activities.needdoingtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.activities.needdoingtasks.di.NeedDoingTasksComponent;
import com.example.andrey.newtmpclient.activities.needdoingtasks.di.NeedDoingTasksModule;
import com.example.andrey.newtmpclient.activities.taskactivity.TaskActivity;
import com.example.andrey.newtmpclient.adapter.TasksAdapter;
import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;
import com.example.andrey.newtmpclient.storage.Updater;


import javax.inject.Inject;

import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;
import static com.example.andrey.newtmpclient.storage.Const.TASK_NUMBER;

public class NeedDoingTasksActivity extends BaseActivity implements NeedDoingTasksView {
    private static final String TAG = NeedDoingTasksActivity.class.getSimpleName();
    @Inject
    NeedDoingTasksPresenter presenter;
    public static final String FROM_DOING_LIST = "fromDoingList";

    private OnListItemClickListener notDoneClickListener = (v, position) -> {
        presenter.getComments(position);
//        Task task = tasksManager.getNeedDoingTasks().get(position);
//        Intent intent = new Intent(this, TaskActivity.class)
//                .putExtra(TASK_NUMBER, task.getId());
//        intent.putExtra(FROM_DOING_LIST, true);
//        new Updater(this, new Request(task, Request.WANT_SOME_COMMENTS), intent).execute();
    };

    @Override
    public void startTaskActivity(int taskId) {
        Intent intent = new Intent(this, TaskActivity.class)
                .putExtra(TASK_NUMBER, taskId);
        intent.putExtra(FROM_DOING_LIST, true);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainTmpActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_doing_tasks);
        changeToolbarTitle("Ближайшие");
        setDialogTitleAndText("Получение комментариев", PLEASE_WAIT);
        ((NeedDoingTasksComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new NeedDoingTasksModule(this))).inject(this);

        RecyclerView tasksList = (RecyclerView) findViewById(R.id.tasks_list);
        tasksList.setLayoutManager(new LinearLayoutManager(this));
        TasksAdapter adapter = new TasksAdapter(TasksManager.INSTANCE.getNeedDoingTasks(), notDoneClickListener);
        tasksList.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            App.getComponentManager().releaseComponent(getClass());
        }
    }

}
