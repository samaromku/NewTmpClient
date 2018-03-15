package com.example.andrey.newtmpclient.activities.needdoingtasks;


import com.example.andrey.newtmpclient.entities.Comment;
import com.example.andrey.newtmpclient.entities.ContactOnAddress;
import com.example.andrey.newtmpclient.managers.CommentsManager;
import com.example.andrey.newtmpclient.managers.ContactsManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.network.TmpService;

import java.util.List;

import io.reactivex.Observable;

public class NeedDoingTasksInterActor {
    private static final String TAG = NeedDoingTasksInterActor.class.getSimpleName();
    private TmpService tmpService;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private CommentsManager commentsManager = CommentsManager.INSTANCE;
    private ContactsManager contactsManager = ContactsManager.Instance;


    public NeedDoingTasksInterActor(TmpService tmpService) {
        this.tmpService = tmpService;
    }

    Observable<Response>getComments(int position){
        return tmpService.getCommentsForTask(Request.requestTaskWithToken(
                TasksManager.INSTANCE.getNeedDoingTasks().get(position), Request.WANT_SOME_COMMENTS));
    }

    Observable<Integer> getCommentsForTask(int position, List<Comment>comments, List<ContactOnAddress>contacts){
        return Observable.fromCallable(() -> {
            contactsManager.removeAll();
            commentsManager.addAll(comments);
            for (ContactOnAddress c : contacts) {
                contactsManager.addContact(c);
            }
            contactsManager.removeEmptyPhonesEmails();
            return tasksManager.getNeedDoingTasks().get(position).getId();
        });
    }
}
