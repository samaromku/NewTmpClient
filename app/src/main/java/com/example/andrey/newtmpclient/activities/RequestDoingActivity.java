package com.example.andrey.newtmpclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.adapter.TasksAdapter;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;
import com.example.andrey.newtmpclient.storage.Updater;
import com.example.andrey.newtmpclient.taskactivity.TaskActivity;

import static com.example.andrey.newtmpclient.storage.Const.TASK_NUMBER;

public class RequestDoingActivity extends AppCompatActivity{
    private TasksManager tasksManager = TasksManager.INSTANCE;
    public static final String FROM_DOING_LIST = "fromDoingList";

    private OnListItemClickListener notDoneClickListener = (v, position) -> {
        Task task = tasksManager.getNeedDoingTasks().get(position);
        Intent intent = new Intent(this, TaskActivity.class)
                .putExtra(TASK_NUMBER, task.getId());
        intent.putExtra(FROM_DOING_LIST, true);
        new Updater(this, new Request(task, Request.WANT_SOME_COMMENTS), intent).execute();
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_doing_activity);
        if(getSupportActionBar()!=null)
        getSupportActionBar().setTitle("Начать выполнение");
        RecyclerView tasksList = (RecyclerView) findViewById(R.id.tasks_list);
        tasksList.setLayoutManager(new LinearLayoutManager(this));
         TasksAdapter adapter = new TasksAdapter(tasksManager.getNeedDoingTasks(), notDoneClickListener);
        tasksList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainTmpActivity.class));
    }
}
