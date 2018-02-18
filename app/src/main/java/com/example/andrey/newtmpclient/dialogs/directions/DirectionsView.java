package com.example.andrey.newtmpclient.dialogs.directions;

import java.util.Date;
import java.util.List;

import com.example.andrey.newtmpclient.entities.User;

public interface DirectionsView {
    void setListToAdapter(List<User> listToAdapter);

    void hideDialog();

    void setUserDateToActivity(User user);

    void updateAdapterPosition(int position);
}
