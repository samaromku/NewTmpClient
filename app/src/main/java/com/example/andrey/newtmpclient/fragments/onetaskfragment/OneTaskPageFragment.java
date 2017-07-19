package com.example.andrey.newtmpclient.fragments.onetaskfragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.AccountActivity;
import com.example.andrey.newtmpclient.adapter.CommentsAdapter;
import com.example.andrey.newtmpclient.adapter.ContactsAdapter;
import com.example.andrey.newtmpclient.entities.Comment;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.CommentsManager;
import com.example.andrey.newtmpclient.managers.ContactsManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.DateUtil;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;
import com.example.andrey.newtmpclient.storage.Updater;

public class OneTaskPageFragment extends Fragment {
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARG_TASK_ID = "arg_task_id";
    private int pageNumber;
    private Button distributed;
    private Button doneBtn;
    private EditText comment;
    private Button needHelp;
    private Button disAgree;
    private Button note;
    private Button doing;

    private RecyclerView commentsList;
    private Task task;
    TasksManager tasksManager = TasksManager.INSTANCE;
    CommentsManager commentsManager = CommentsManager.INSTANCE;
    UsersManager usersManager = UsersManager.INSTANCE;
    Comment newComment;
    private ContactsManager contactsManager = ContactsManager.Instance;

    private OnListItemClickListener clickListener = (v, position) -> {};

    public static OneTaskPageFragment newInstance(int page, int taskId){
        OneTaskPageFragment ScreenSlidePageFragment = new OneTaskPageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putInt(ARG_TASK_ID, taskId);
        ScreenSlidePageFragment.setArguments(arguments);
        return ScreenSlidePageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        task = tasksManager.getById(getArguments().getInt(ARG_TASK_ID));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = null;

        if(pageNumber==0){
            rootView = (ViewGroup) inflater.inflate(R.layout.one_task_fragment, container, false);
            initiate(rootView);
            commentsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            CommentsAdapter adapter = new CommentsAdapter(commentsManager.getCommentsByTaskId(task.getId()), clickListener);
            commentsList.setAdapter(adapter);
            btnClicks();
            setEnableBtns();
        }else if(pageNumber==1){
            rootView = (ViewGroup) inflater.inflate(R.layout.contacts_fragment, container, false);
            initiateContacts(rootView);
        }

        return rootView;
    }

    private void initiateContacts(ViewGroup rootView) {
        RecyclerView contactsList = (RecyclerView) rootView.findViewById(R.id.contacts_list);
        contactsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        ContactsAdapter adapter = new ContactsAdapter(contactsManager.getContactsList(), clickListener);
        contactsList.setAdapter(adapter);
    }

    private void setEnableBtns(){
//        - новая, помощь, отказ - взять себе доступна, остальные блок
        if(task.getStatus().equals(TaskEnum.NEW_TASK) ||
                task.getStatus().equals(TaskEnum.DISAGREE_TASK) ||
                task.getStatus().equals(TaskEnum.NEED_HELP) ||
                task.getStatus().equals(TaskEnum.CONTROL_TASK)){
            setBtnDisable(needHelp);
            setBtnDisable(disAgree);
            setBtnDisable(doneBtn);
            setBtnDisable(doing);
        }
//        - распределена - взять себе блок, перевести в выполненные блок остальные доступны
        else if(task.getStatus().equals(TaskEnum.DISTRIBUTED_TASK)){
            setBtnDisable(distributed);
        }

        else if(task.getStatus().equals(TaskEnum.DOING_TASK)){
            setBtnDisable(distributed);
            setBtnDisable(doing);
        }


//        - выполненные - неважно
    }

    private void setBtnDisable(Button btn){
        btn.setEnabled(false);
        btn.setBackgroundColor(Color.parseColor("#969696"));
    }

    private void initiate(ViewGroup rootView){
        tasksManager.setTask(task);
        commentsList = (RecyclerView) rootView.findViewById(R.id.comments);
        TextView typeTask = (TextView) rootView.findViewById(R.id.type_task);
        TextView importance = (TextView) rootView.findViewById(R.id.importance);
        TextView orgName = (TextView) rootView.findViewById(R.id.org_name);
        TextView address = (TextView) rootView.findViewById(R.id.address);
        TextView taskBody = (TextView) rootView.findViewById(R.id.task_body);
        TextView deadLine = (TextView) rootView.findViewById(R.id.deadline);
        TextView userLogin = (TextView) rootView.findViewById(R.id.user_login);

        distributed = (Button) rootView.findViewById(R.id.distibuted);
        doneBtn = (Button) rootView.findViewById(R.id.done);
        needHelp = (Button) rootView.findViewById(R.id.need_help);
        disAgree = (Button) rootView.findViewById(R.id.disagree);
        note = (Button) rootView.findViewById(R.id.note);
        doing = (Button) rootView.findViewById(R.id.doing);
        comment = (EditText) rootView.findViewById(R.id.commentFromUser);

        typeTask.setText(task.getType());
        importance.setText(task.getImportance());
        orgName.setText(task.getOrgName());
        address.setText(task.getAddress());
        taskBody.setText(task.getBody());
        deadLine.setText(task.getDoneTime());
        //заглушка на удаленного пользователя
        User userWithNotName = usersManager.getUserById(task.getUserId());
        if(userWithNotName!=null) {
            userLogin.setText(usersManager.getUserById(task.getUserId()).getLogin());
        }else userLogin.setText("Удален");
    }

    private void btnClicks(){
        doneBtn.setOnClickListener(v -> clickOnButton(TaskEnum.CONTROL_TASK));
        needHelp.setOnClickListener(v -> clickOnButton(TaskEnum.NEED_HELP));
        disAgree.setOnClickListener(v -> clickOnButton(TaskEnum.DISAGREE_TASK));
        note.setOnClickListener(v -> addComment());
        doing.setOnClickListener(v -> clickOnButton(TaskEnum.DOING_TASK));
        distributed.setOnClickListener(v -> clickToChangeStatus(TaskEnum.DISTRIBUTED_TASK));
    }


    private void addComment(){
        if(comment.getText().toString().equals("")){
            comment.setHint("Вы должны заполнить это поле");
        }else {
            //создаем новый коммент
            newComment = new Comment(
                    new DateUtil().currentDate(),
                    comment.getText().toString(),
                    usersManager.getUser().getId(),
                    task.getId());
            tasksManager.setStatus(task.getStatus());
            //добавить в бдб отправить на сервер
            commentsManager.setComment(newComment);
            commentsManager.removeAll();
            Intent intent = new Intent(getActivity(), AccountActivity.class).putExtra("statusChanged", true);
            new Updater(getActivity(), new Request(newComment, TaskEnum.NOTE), intent).execute();
        }
    }

    private void clickOnButton(String actionTask){
        if(comment.getText().toString().equals("")){
            comment.setHint("Вы должны заполнить это поле");
        }
        else {
            //создаем новый коммент
            newComment = new Comment(
                    new DateUtil().currentDate(),
                    comment.getText().toString(),
                    usersManager.getUser().getId(),
                    task.getId());
            //добавить в бдб отправить на сервер
            tasksManager.setStatus(actionTask);
            commentsManager.setComment(newComment);
            commentsManager.removeAll();
            Intent intent = new Intent(getActivity(), AccountActivity.class).putExtra("statusChanged", true);
            new Updater(getActivity(), new Request(newComment, actionTask), intent).execute();
        }
    }

    //юзер решил взять заявку себе
    private void clickToChangeStatus(String changedStatusTask){
        task.setUserId(usersManager.getUser().getId());
        task.setStatus(changedStatusTask);
        tasksManager.setTask(task);
        commentsManager.removeAll();
        Intent intent = new Intent(getActivity(), AccountActivity.class).putExtra("statusChanged", true);
        new Updater(getActivity(), new Request(task, changedStatusTask), intent).execute();
    }
}
