package com.example.andrey.newtmpclient.activities.address;

import java.util.List;

import com.example.andrey.newtmpclient.entities.Address;

public interface AddressMvpView {
    void setListToAdapter(List<Address> listToAdapter);

    void showDialog();

    void hideDialog();
}
