package com.example.andrey.newtmpclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.adapter.TasksAdapter;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;
import com.example.andrey.newtmpclient.storage.Updater;

public class RequestDoingActivity extends AppCompatActivity{
    private RecyclerView tasksList;
    private TasksAdapter adapter;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private User user;
    public static final String FROM_DOING_LIST = "fromDoingList";

    private OnListItemClickListener notDoneClickListener = (v, position) -> {
        Task task = tasksManager.getNeedDoingTasks().get(position);
        Intent intent = new Intent(this, TaskActivity.class).putExtra("taskNumber", task.getId());
        intent.putExtra(FROM_DOING_LIST, true);
        new Updater(this, new Request(task, Request.WANT_SOME_COMMENTS), intent).execute();
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_doing_activity);
        getSupportActionBar().setTitle("Начать выполнение");
        user = usersManager.getUser();
        tasksList = (RecyclerView) findViewById(R.id.tasks_list);
        tasksList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TasksAdapter(tasksManager.getNeedDoingTasks(), notDoneClickListener);
        tasksList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, AccountActivity.class));
    }
}
