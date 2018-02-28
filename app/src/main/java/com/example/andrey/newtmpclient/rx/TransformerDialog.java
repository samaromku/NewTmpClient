package com.example.andrey.newtmpclient.rx;

import com.example.andrey.newtmpclient.base.basemvp.BaseView;
import com.example.andrey.newtmpclient.network.ApiResponse;
import com.example.andrey.newtmpclient.network.Response;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.andrey.newtmpclient.network.Response.NOT_SUCCESS;
import static com.example.andrey.newtmpclient.network.Response.NOT_SUCCESS_AUTH;
import static com.example.andrey.newtmpclient.storage.Const.AUTH_FAILED;
import static com.example.andrey.newtmpclient.storage.Const.RESPONSE_FAILED;

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
                .doOnTerminate(() -> view.hideDialog())
                .compose(checkIfSuccess());
    }

    private ObservableTransformer<T, T> checkIfSuccess() {
        return o -> o.flatMap(tResponse -> {
            if (tResponse instanceof Response) {
                Response response = (Response) tResponse;
                if (response.getResponse().equals(NOT_SUCCESS)) {
                    return Observable.error(new Throwable(RESPONSE_FAILED));
                } else if (response.getResponse().equals(NOT_SUCCESS_AUTH)) {
                    return Observable.error(new Throwable(AUTH_FAILED));
                }
            }
            if (tResponse instanceof ApiResponse) {
                ApiResponse response = (ApiResponse) tResponse;
                if (response.getResponse().equals(NOT_SUCCESS)) {
                    return Observable.error(new Throwable(RESPONSE_FAILED));
                } else if (response.getResponse().equals(NOT_SUCCESS_AUTH)) {
                    return Observable.error(new Throwable(AUTH_FAILED));
                }
            }
            return Observable.just(tResponse);
        });
    }


}
