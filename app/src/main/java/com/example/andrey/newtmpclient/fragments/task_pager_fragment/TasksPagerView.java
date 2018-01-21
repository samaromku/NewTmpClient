package com.example.andrey.newtmpclient.fragments.task_pager_fragment;

import com.example.andrey.newtmpclient.entities.Task;

import java.util.List;

public interface TasksPagerView {
    void setListToAdapter(List<Task>listToAdapter);
}
