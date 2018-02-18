package com.example.andrey.newtmpclient.rx;

import com.example.andrey.newtmpclient.base.basemvp.BaseView;
import com.example.andrey.newtmpclient.network.ApiResponse;
import com.example.andrey.newtmpclient.network.Response;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.andrey.newtmpclient.network.Response.NOT_SUCCESS_AUTH;

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
                .doOnNext(t -> {
                    if(t instanceof Response){
                        Response response = (Response) t;
                        if(response.getResponse().equals(NOT_SUCCESS_AUTH)){
                            view.notSuccessAuth();
                        }
                    } else if(t instanceof ApiResponse){
                        ApiResponse apiResponse = (ApiResponse) t;
                        if(apiResponse.getResponse().equals(NOT_SUCCESS_AUTH)){
                            view.notSuccessAuth();
                        }
                    }
                });
    }
}
