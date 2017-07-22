package com.example.andrey.newtmpclient.fragments.one_task_fragment.presenter;

import com.example.andrey.newtmpclient.entities.Comment;
import com.example.andrey.newtmpclient.entities.ContactOnAddress;

import java.util.List;

/**
 * Created by andrey on 19.07.2017.
 */

public interface OneTaskFragmentPresenter {

    List<Comment>getCommentsFromInteractor();

    List<ContactOnAddress>getContactsFromInteractor();

    void addNesessaryCommentTaskStatus(String comment, String taskStatus);

    void changeStatusWithoutComment(String taskStatus);

    void onDestroy();

    String getStatusTaskFromInteractor();

    void userTakesTask(String status);

    void initFields();
}
