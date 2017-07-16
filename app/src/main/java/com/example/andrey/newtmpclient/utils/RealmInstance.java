package com.example.andrey.newtmpclient.utils;

import android.util.Log;

import com.example.andrey.newtmpclient.entities.Address;

import java.util.List;

import io.realm.Realm;

import static android.content.ContentValues.TAG;

/**
 * Created by andrey on 14.07.2017.
 */

public class RealmInstance {
    Realm realm;
    public static final RealmInstance instance = new RealmInstance();

    private RealmInstance(){
        realm = Realm.getDefaultInstance();
    }

    public List<Address>getAllAddresses(){
        return realm.where(Address.class).findAll();
    }

    public void addAllAddresses(List<Address>addresses){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(addresses);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "onSuccess: ");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.i(TAG, "onError: ");
            }
        });
//        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(addresses));
    }
}
