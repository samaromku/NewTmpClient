package com.example.andrey.newtmpclient.fragments.one_task_fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.maindrawer.MainTmpActivity;
import com.example.andrey.newtmpclient.adapter.CommentsAdapter;
import com.example.andrey.newtmpclient.adapter.ContactsAdapter;
import com.example.andrey.newtmpclient.entities.Comment;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.fragments.one_task_fragment.presenter.OneTaskFragmentPresenter;
import com.example.andrey.newtmpclient.fragments.one_task_fragment.presenter.OneTaskFragmentPresenterImpl;
import com.example.andrey.newtmpclient.fragments.one_task_fragment.view.OneTaskView;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;
import com.example.andrey.newtmpclient.storage.Updater;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.example.andrey.newtmpclient.utils.Const.STATUS_CHANGED;

public class OneTaskPageFragment extends Fragment implements OneTaskView {
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
    private TextView etTypeTask;
    private TextView etImportance;
    private TextView etOrgName;
    private TextView etAddress;
    private TextView etTaskBody;
    private TextView etDeadLine;
    private TextView etUserLogin;
    private OneTaskFragmentPresenter oneTaskFragmentPresenter;
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;


    private OnListItemClickListener clickListener = (v, position) -> {
    };

    public static OneTaskPageFragment newInstance(int page, int taskId) {
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
        oneTaskFragmentPresenter = new OneTaskFragmentPresenterImpl(this, getArguments().getInt(ARG_TASK_ID));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = null;

        if (pageNumber == 0) {
            rootView = (ViewGroup) inflater.inflate(R.layout.one_task_fragment, container, false);
            initiate(rootView);
            commentsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            oneTaskFragmentPresenter.initFields();
            CommentsAdapter adapter = new CommentsAdapter(oneTaskFragmentPresenter.getCommentsFromInteractor(), clickListener);
            commentsList.setAdapter(adapter);
        } else if (pageNumber == 1) {
            rootView = (ViewGroup) inflater.inflate(R.layout.contacts_fragment, container, false);
            initiateContacts(rootView);
        }
        return rootView;
    }

    private void startInputVoice() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Скажите что-нибудь");
        try {
            startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,   Uri.parse("https://market.android.com/details?id=APP_PACKAGE_NAME"));
            startActivity(browserIntent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            comment.append(" " + data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
            Log.i(TAG, "onActivityResult: " + data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initiate(ViewGroup rootView) {
        commentsList = rootView.findViewById(R.id.comments);
        etTypeTask = rootView.findViewById(R.id.type_task);
        etImportance = rootView.findViewById(R.id.importance);
        etOrgName = rootView.findViewById(R.id.org_name);
        etAddress = rootView.findViewById(R.id.address);
        etTaskBody = rootView.findViewById(R.id.task_body);
        etDeadLine = rootView.findViewById(R.id.deadline);
        etUserLogin = rootView.findViewById(R.id.user_login);

        distributed = rootView.findViewById(R.id.distibuted);
        doneBtn = rootView.findViewById(R.id.done);
        needHelp = rootView.findViewById(R.id.need_help);
        disAgree = rootView.findViewById(R.id.disagree);
        note = rootView.findViewById(R.id.note);
        doing = rootView.findViewById(R.id.doing);
        comment = rootView.findViewById(R.id.commentFromUser);

        doneBtn.setOnClickListener(v -> oneTaskFragmentPresenter.addNesessaryCommentTaskStatus(comment.getText().toString(), TaskEnum.CONTROL_TASK));
        needHelp.setOnClickListener(v -> oneTaskFragmentPresenter.addNesessaryCommentTaskStatus(comment.getText().toString(), TaskEnum.NEED_HELP));
        disAgree.setOnClickListener(v -> oneTaskFragmentPresenter.addNesessaryCommentTaskStatus(comment.getText().toString(), TaskEnum.DISAGREE_TASK));
        note.setOnClickListener(v -> oneTaskFragmentPresenter.addNesessaryCommentTaskStatus(comment.getText().toString(), TaskEnum.NOTE));
        doing.setOnClickListener(v -> oneTaskFragmentPresenter.userTakesTask(TaskEnum.DOING_TASK));
        distributed.setOnClickListener(v -> oneTaskFragmentPresenter.userTakesTask(TaskEnum.DISTRIBUTED_TASK));
        ImageButton btnVoice = rootView.findViewById(R.id.btnMic);
        btnVoice.setOnClickListener(view -> startInputVoice());
    }

    private void initiateContacts(ViewGroup rootView) {
        RecyclerView contactsList = (RecyclerView) rootView.findViewById(R.id.contacts_list);
        contactsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        ContactsAdapter adapter = new ContactsAdapter(oneTaskFragmentPresenter.getContactsFromInteractor(), clickListener);
        contactsList.setAdapter(adapter);
    }

    @Override
    public void setDisableDone(boolean isEnable) {
        setBtnDisableAndGrayBackground(doneBtn, isEnable);
    }

    @Override
    public void setDisableDistributed(boolean isEnable) {
        setBtnDisableAndGrayBackground(distributed, isEnable);
    }

    @Override
    public void setDisableNeedHelp(boolean isEnable) {
        setBtnDisableAndGrayBackground(needHelp, isEnable);
    }

    @Override
    public void setDisableDoing(boolean isEnable) {
        setBtnDisableAndGrayBackground(doing, isEnable);
    }

    @Override
    public void setDisableDisagree(boolean isEnable) {
        setBtnDisableAndGrayBackground(disAgree, isEnable);
    }

    private void setBtnDisableAndGrayBackground(Button btn, boolean isEnable) {
        btn.setEnabled(isEnable);
        btn.setBackgroundColor(Color.parseColor("#969696"));
    }

    @Override
    public void setType(String type) {
        etTypeTask.setText(type);
    }

    @Override
    public void setImportance(String importance) {
        etImportance.setText(importance);
    }

    @Override
    public void setOrgName(String orgName) {
        etOrgName.setText(orgName);
    }

    @Override
    public void setAddress(String address) {
        etAddress.setText(address);
    }

    @Override
    public void setBody(String taskBody) {
        etTaskBody.setText(taskBody);
    }

    @Override
    public void setDeadLine(String deadLine) {
        etDeadLine.setText(deadLine);
    }

    @Override
    public void setUserName(String userName) {
        etUserLogin.setText(userName);
    }

    @Override
    public void setHitComment(String hint) {
        comment.setHint(hint);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        oneTaskFragmentPresenter.onDestroy();
    }

    @Override
    public void startActivityWithComment(Comment newComment, String status) {
        Log.i(TAG, "startActivityWithComment: sendMessage");
        Intent intent = new Intent(getActivity(), MainTmpActivity.class).putExtra(STATUS_CHANGED, true);
        new Updater(getActivity(), new Request(newComment, status), intent).execute();
    }

    @Override
    public void startActivityWithTask(Task task) {
        Log.i(TAG, "startActivityWithTask: sendMessage");
        Intent intent = new Intent(getActivity(), MainTmpActivity.class).putExtra("statusChanged", true);
        new Updater(getActivity(), new Request(task, task.getStatus()), intent).execute();
    }
}
