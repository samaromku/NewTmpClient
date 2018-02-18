package com.example.andrey.newtmpclient.interfaces;

import com.example.andrey.newtmpclient.entities.User;

import java.util.Date;

/**
 * Created by savchenko on 18.02.18.
 */

public interface UserDateGetter {
    void onUserDateGet(User user, Date date);
}
