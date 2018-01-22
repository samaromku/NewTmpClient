package com.example.andrey.newtmpclient.fragments.notdonetasks;

import com.example.andrey.newtmpclient.entities.Task;

import java.util.List;

public interface NotDoneTasksView {
    void setListToAdapter(List<Task> listToAdapter);
}
