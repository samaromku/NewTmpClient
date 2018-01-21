package com.example.andrey.newtmpclient.fragments.task_pager_fragment;

import android.util.Log;

import com.example.andrey.newtmpclient.storage.AuthChecker;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TasksPagerPresenter {
    private static final String TAG = TasksPagerPresenter.class.getSimpleName();
    private TasksPagerView view;
    private TasksPagerInterActor interActor;

    public TasksPagerPresenter(TasksPagerView view, TasksPagerInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void updateTasks() {
        interActor.updateTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    view.setListToAdapter(response.getTaskList());
                },
                throwable -> Log.e(TAG, throwable.getMessage(), throwable));
    }

}
