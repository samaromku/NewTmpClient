package com.example.andrey.newtmpclient.fragments.one_task_fragment.interactor;

import android.text.TextUtils;

import com.example.andrey.newtmpclient.entities.Comment;
import com.example.andrey.newtmpclient.entities.ContactOnAddress;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.CommentsManager;
import com.example.andrey.newtmpclient.managers.ContactsManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.storage.DateUtil;
import com.example.andrey.newtmpclient.utils.Const;

import java.util.List;

import static com.example.andrey.newtmpclient.entities.TaskEnum.CONTROL_TASK;
import static com.example.andrey.newtmpclient.entities.TaskEnum.DISAGREE_TASK;
import static com.example.andrey.newtmpclient.entities.TaskEnum.DISTRIBUTED_TASK;
import static com.example.andrey.newtmpclient.entities.TaskEnum.DOING_TASK;
import static com.example.andrey.newtmpclient.entities.TaskEnum.NEED_HELP;
import static com.example.andrey.newtmpclient.entities.TaskEnum.NEW_TASK;

/**
 * Created by andrey on 19.07.2017.
 */

public class OneTaskFragmentInteractorImpl implements OneTaskFragmentInteractor {
    private Task task;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private CommentsManager commentsManager = CommentsManager.INSTANCE;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private ContactsManager contactsManager = ContactsManager.Instance;
    private int taskId;
    private OnInitTask onInitTask;
    private OnActionsBtns onActionsBtns;

    public OneTaskFragmentInteractorImpl(int taskId, OnInitTask onInitTask) {
        this.taskId = taskId;
        this.onInitTask = onInitTask;
    }

    @Override
    public void initFields() {
        initTask();
        checkTaskStatusBtnsEnabled();
    }

    @Override
    public void setOnActionsBtns(OnActionsBtns onActionsBtns) {
        this.onActionsBtns = onActionsBtns;
    }

    @Override
    public List<ContactOnAddress> contactsOnAddress() {
        return contactsManager.getContactsList();
    }

    private void initTask(){
        task = tasksManager.getById(taskId);
        tasksManager.setTask(task);

        onInitTask.setImportance(task.getImportance());
        onInitTask.setType(task.getType());
        onInitTask.setDeadLine(task.getDoneTime());
        onInitTask.setBody(task.getBody());
        onInitTask.setAddress(task.getAddress());
        onInitTask.setOrgName(task.getOrgName());


        //заглушка на удаленного пользователя
        User userWithNotName = usersManager.getUserById(task.getUserId());
        if(userWithNotName!=null) {
            onInitTask.setUserName(usersManager.getUserById(task.getUserId()).getLogin());
        }else {
            onInitTask.setUserName(Const.DELETED);
        }
    }

    @Override
    public List<Comment> getCommentsForTask() {
        return commentsManager.getCommentsByTaskId(taskId);
    }

    @Override
    public void userTakesTask(String changedStatusTask) {
        task.setUserId(usersManager.getUser().getId());
        task.setStatus(changedStatusTask);
        tasksManager.setTask(task);
        commentsManager.removeAll();
        onActionsBtns.startActivityWithTask(task);
    }

    @Override
    public void checkTaskStatusBtnsEnabled() {
        //        - новая, помощь, отказ - взять себе доступна, остальные блок
        switch (task.getStatus()) {
            case NEW_TASK:
            case DISAGREE_TASK:
            case NEED_HELP:
            case CONTROL_TASK:
                onInitTask.onSetDisableDisagree(false);
                onInitTask.onSetDisableNeedHelp(false);
                onInitTask.onSetDisableDoing(false);
                onInitTask.onSetDisableDone(false);
                break;
            //        - распределена - взять себе блок, перевести в выполненные блок остальные доступны
            case DISTRIBUTED_TASK:
                onInitTask.onSetDisableDistributed(false);
                break;
            case DOING_TASK:
                onInitTask.onSetDisableDistributed(false);
                onInitTask.onSetDisableDoing(false);
                break;
        }
    }

    @Override
    public boolean checkIfCommentFilled(String comment) {
        return !TextUtils.isEmpty(comment);
    }

    @Override
    public void addComment(String comment, String status) {
            //создаем новый коммент
        String taskStatus;
        String requestAction;
        if(status.equals(TaskEnum.NOTE)) {
            taskStatus = task.getStatus();
            requestAction = TaskEnum.NOTE;
        }else {
            taskStatus = status;
            requestAction = status;
        }
        Comment newComment = new Comment(
                new DateUtil().currentDate(),
                comment,
                usersManager.getUser().getId(),
                task.getId());
            tasksManager.setStatus(taskStatus);
            //добавить в бдб отправить на сервер
            commentsManager.setComment(newComment);
            commentsManager.removeAll();

            onActionsBtns.startActivityAfterCreate(newComment, requestAction);
    }

    @Override
    public String getStatusTask() {
        return task.getStatus();
    }
}
