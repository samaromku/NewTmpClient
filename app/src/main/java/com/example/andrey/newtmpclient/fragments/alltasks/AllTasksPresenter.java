package com.example.andrey.newtmpclient.fragments.alltasks;

import android.util.Log;

import com.example.andrey.newtmpclient.entities.ContactOnAddress;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.managers.CommentsManager;
import com.example.andrey.newtmpclient.managers.ContactsManager;
import com.example.andrey.newtmpclient.network.ApiResponse;
import com.example.andrey.newtmpclient.rx.TransformerDialog;
import com.example.andrey.newtmpclient.utils.Utils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.andrey.newtmpclient.utils.Utils.showError;

public class AllTasksPresenter {
    private static final String TAG = AllTasksPresenter.class.getSimpleName();
    private AllTasksView view;
    private AllTasksInterActor interActor;
    private CommentsManager commentsManager = CommentsManager.INSTANCE;
    private ContactsManager contactsManager = ContactsManager.Instance;

    public AllTasksPresenter(AllTasksView view, AllTasksInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void updateTasks(boolean done) {
        interActor.updateTasks(done)
                .compose(new TransformerDialog<>(view))
                .subscribe(apiResponse -> {
                            List<Task> tasks = apiResponse.getData();
                            if (done) {
                                interActor.setDoneTasks(tasks);
                            } else {
                                interActor.setNotDoneTasks(tasks);
                            }
                            view.setListToAdapter(tasks);
                        },
                        throwable -> showError(view, throwable));
    }

    void getSearchedList(String search, boolean done) {
        interActor.searchedTasks(search, done)
                .subscribe(tasks -> view.setListToAdapter(tasks),
                        throwable -> showError(view, throwable));
    }

    void getDoneTasksIfEmpty(boolean done) {
        if (done) {
            interActor.getDoneTasksIfEmpty()
                    .compose(new TransformerDialog<>(view))
                    .map(ApiResponse::getData)
                    .subscribe(tasks ->
                            interActor.setDoneTasks(tasks)
                                    .subscribe(() -> view.setListToAdapter(tasks),
                                            throwable -> Utils.showError(view, throwable)));
        }
    }

    void getComments(int position) {
        interActor.getComments(position)
                .compose(new TransformerDialog<>(view))
                .subscribe(response -> {
                            contactsManager.removeAll();
                            commentsManager.addAll(response.getComments());
                            for (ContactOnAddress c : response.getContacts()) {
                                contactsManager.addContact(c);
                            }
                            contactsManager.removeEmptyPhonesEmails();
                            view.startCreateTaskActivity(interActor.getTaskId());
                        },
                        throwable -> showError(view, throwable));
    }

    void getFirstAddresses() {
        interActor.getFirstAddresses()
                .compose(new TransformerDialog<>(view))
                .subscribe(response -> {
                            interActor.setAddresses(response.getData()).subscribe();
                            view.startCreateTaskActivity();
                        },
                        throwable -> showError(view, throwable));
    }

    void getTasksByFilter(int days, boolean done) {
        interActor.getTasksByFilter(days, done)
                .subscribe(tasks -> view.setListToAdapter(tasks),
                        throwable -> showError(view, throwable));
    }

    void addFireBaseToken() {
        interActor.addFireBaseToken()
                .compose(new TransformerDialog<>(view))
                .subscribe(booleanApiResponse -> Log.i(TAG, "addFireBaseToken: token success added"),
                        Throwable::printStackTrace);
    }
}
