package com.example.andrey.newtmpclient.activities.taskactivity;

import com.example.andrey.newtmpclient.rx.TransformerDialog;
import com.example.andrey.newtmpclient.utils.Utils;

public class OneTaskPresenter {
    private static final String TAG = OneTaskPresenter.class.getSimpleName();
    private OneTaskView view;
    private OneTaskInterActor interActor;

    public OneTaskPresenter(OneTaskView view, OneTaskInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void removeTask(int taskId) {
        interActor.removeTask(taskId)
                .compose(new TransformerDialog<>(view))
                .subscribe(response -> {
                    interActor.removeTask().subscribe(() -> {
                        view.startMainActivity("removeTask");
                    });
                }, throwable -> {
                    Utils.showError(view, throwable);
                });
    }

    void changeTaskStatus(String changedStatusTask, int taskId){
        interActor.changeStatus(changedStatusTask, taskId)
                .compose(new TransformerDialog<>(view))
                .subscribe(response ->
                        interActor.updateTask()
                        .subscribe(() -> view.startMainActivity("statusChanged")), throwable -> {
                    Utils.showError(view, throwable);
                });
    }

    void getAddresses(){
        interActor.getFirstAddresses()
                .compose(new TransformerDialog<>(view))
                .subscribe(response -> {
                    interActor.setAddresses(response.getData())
                            .subscribe(() -> view.startUpdateActivity());
                }, throwable -> {
                    Utils.showError(view, throwable);
                });
    }
}
