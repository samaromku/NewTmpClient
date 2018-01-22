package com.example.andrey.newtmpclient.fragments.address;

import java.util.List;

import com.example.andrey.newtmpclient.base.basemvp.BaseView;
import com.example.andrey.newtmpclient.entities.Address;

public interface AddressMvpView extends BaseView{
    void setListToAdapter(List<Address> listToAdapter);

}
