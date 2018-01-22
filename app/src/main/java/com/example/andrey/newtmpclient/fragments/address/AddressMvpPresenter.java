package com.example.andrey.newtmpclient.fragments.address;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddressMvpPresenter {
    private static final String TAG = AddressMvpPresenter.class.getSimpleName();
    private AddressMvpView view;
    private AddressMvpInterActor interActor;

    public AddressMvpPresenter(AddressMvpView view, AddressMvpInterActor interActor) {
        this.view = view;
        this.interActor = interActor;
    }

    void getListFroAdapter() {
        interActor.getListFroAdapter()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.showDialog())
                .doOnTerminate(() -> view.hideDialog())
                .subscribe(list -> view.setListToAdapter(list),
                        throwable -> Log.e(TAG, throwable.getMessage(), throwable));
    }
}
