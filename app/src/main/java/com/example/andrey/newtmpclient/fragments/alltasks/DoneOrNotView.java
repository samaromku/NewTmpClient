package com.example.andrey.newtmpclient.fragments.alltasks;

import com.example.andrey.newtmpclient.entities.Task;

import java.util.List;

/**
 * Created by savchenko on 22.01.18.
 */

public interface DoneOrNotView {
    void setListToAdapter(List<Task> listToAdapter);
}
