package com.example.andrey.newtmpclient.fragments.alltasks.donetasks;

import android.util.Log;

import com.example.andrey.newtmpclient.fragments.alltasks.DoneOrNotView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DoneTasksPresenter {
    private static final String TAG = DoneTasksPresenter.class.getSimpleName();
    private DoneOrNotView view;
    private DoneTasksInterActor interActor;

    public DoneTasksPresenter(DoneOrNotView view, DoneTasksInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    public void updateTasks(boolean done) {
        interActor.updateTasks(done)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> view.setListToAdapter(list),
                throwable -> Log.e(TAG, throwable.getMessage(), throwable));
    }

    public void getSearchedList(String search, boolean done){
        interActor.searchedTasks(search, done)
                .subscribe(tasks -> {
                    view.setListToAdapter(tasks);
                }, throwable -> throwable.printStackTrace());
    }

}
