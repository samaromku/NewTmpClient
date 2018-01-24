package com.example.andrey.newtmpclient.rx;

import com.example.andrey.newtmpclient.base.basemvp.BaseView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by savchenko on 24.01.18.
 */

public class TransformerDialog<T> implements ObservableTransformer<T, T> {
    private BaseView view;

    public TransformerDialog(BaseView view) {
        this.view = view;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.showDialog())
                .doOnTerminate(() -> view.hideDialog());
    }
}
