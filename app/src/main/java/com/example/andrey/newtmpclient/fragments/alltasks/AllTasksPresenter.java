package com.example.andrey.newtmpclient.fragments.alltasks;

import android.util.Log;

import com.example.andrey.newtmpclient.entities.ContactOnAddress;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.managers.CommentsManager;
import com.example.andrey.newtmpclient.managers.ContactsManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.andrey.newtmpclient.storage.Const.ERROR_DATA;

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> view.setListToAdapter(list),
                        throwable -> Log.e(TAG, throwable.getMessage(), throwable));
    }

    void getSearchedList(String search, boolean done) {
        interActor.searchedTasks(search, done)
                .subscribe(tasks -> {
                    view.setListToAdapter(tasks);
                }, throwable -> throwable.printStackTrace());
    }

    void getComments(Task task) {
        interActor.getComments(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.showDialog())
                .doOnTerminate(() -> view.hideDialog())
                .subscribe(response -> {
                            contactsManager.removeAll();
                            commentsManager.addAll(response.getComments());
                            for(ContactOnAddress c:response.getContacts()){
                                contactsManager.addContact(c);
                            }
                            contactsManager.removeEmptyPhonesEmails();
                            view.startTaskActivity(task.getId());
                        },
                        throwable -> view.showToast(ERROR_DATA));
    }

}
