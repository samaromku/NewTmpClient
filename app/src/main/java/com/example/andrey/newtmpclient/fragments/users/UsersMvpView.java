package com.example.andrey.newtmpclient.fragments.users;

import java.util.List;

import com.example.andrey.newtmpclient.entities.User;

public interface UsersMvpView {
    void setListToAdapter(List<User> listToAdapter);
}
