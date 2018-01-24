package com.example.andrey.newtmpclient.activities.updatenewtask;

import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.rx.TransformerDialog;
import com.example.andrey.newtmpclient.utils.Utils;

public class UpdateNewTaskPresenter {
    private static final String TAG = UpdateNewTaskPresenter.class.getSimpleName();
    private UpdateNewTaskView view;
    private UpdateNewTaskInterActor interActor;

    public UpdateNewTaskPresenter(UpdateNewTaskView view, UpdateNewTaskInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void updateTask(Task task){
        interActor.updateTask(task)
                .compose(new TransformerDialog<>(view))
        .subscribe(response -> {
            interActor.successUpdate().subscribe();
            view.startMainActivity();
        }, throwable -> Utils.showError(view, throwable));
    }
}
