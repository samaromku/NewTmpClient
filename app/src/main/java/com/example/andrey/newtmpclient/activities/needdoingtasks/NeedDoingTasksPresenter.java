package com.example.andrey.newtmpclient.activities.needdoingtasks;

import com.example.andrey.newtmpclient.rx.TransformerDialog;
import com.example.andrey.newtmpclient.utils.Utils;

public class NeedDoingTasksPresenter {
    private static final String TAG = NeedDoingTasksPresenter.class.getSimpleName();
    private NeedDoingTasksView view;
    private NeedDoingTasksInterActor interActor;

    public NeedDoingTasksPresenter(NeedDoingTasksView view, NeedDoingTasksInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void getComments(int position){
        interActor.getComments(position)
                .compose(new TransformerDialog<>(view))
        .subscribe(response -> {
            interActor.getCommentsForTask(position, response.getComments(), response.getContacts())
                    .subscribe(integer -> view.startTaskActivity(integer));
        }, throwable -> Utils.showError(view, throwable));
    }

}
