package com.example.andrey.newtmpclient.fragments.address;

import android.util.Log;

import com.example.andrey.newtmpclient.network.ApiResponse;
import com.example.andrey.newtmpclient.rx.TransformerDialog;

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
                .compose(new TransformerDialog<>(view))
                .map(ApiResponse::getData)
                .subscribe(list -> view.setListToAdapter(list),
                        throwable -> Log.e(TAG, throwable.getMessage(), throwable));
    }
}
