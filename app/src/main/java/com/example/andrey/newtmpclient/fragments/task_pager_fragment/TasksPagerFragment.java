package com.example.andrey.newtmpclient.fragments.task_pager_fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.adapter.TasksAdapter;
import com.example.andrey.newtmpclient.base.BaseFragment;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.fragments.task_pager_fragment.di.TasksPagerComponent;
import com.example.andrey.newtmpclient.fragments.task_pager_fragment.di.TasksPagerModule;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.AuthChecker;
import com.example.andrey.newtmpclient.storage.ConverterMessages;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;
import com.example.andrey.newtmpclient.storage.Updater;
import com.example.andrey.newtmpclient.taskactivity.TaskActivity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import javax.inject.Inject;

public class TasksPagerFragment extends BaseFragment implements TasksPagerView {
    private static final String TAG = TasksPagerFragment.class.getSimpleName();
    @Inject
    TasksPagerPresenter presenter;
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    int pageNumber;
    private TasksAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private TasksManager tasksManager = TasksManager.INSTANCE;

    private OnListItemClickListener notDoneClickListener = (v, position) -> {
        Task task = tasksManager.getNotDoneTasks().get(position);
        Intent intent = new Intent(getActivity(), TaskActivity.class).putExtra("taskNumber", task.getId());
        new Updater(getActivity(), new Request(task, Request.WANT_SOME_COMMENTS), intent).execute();
    };

    private OnListItemClickListener doneClickListener = (v, position) -> {
        Task task = tasksManager.getDoneTasks().get(position);
        Intent intent = new Intent(getActivity(), TaskActivity.class).putExtra("taskNumber", task.getId());
        new Updater(getActivity(), new Request(task, Request.WANT_SOME_COMMENTS), intent).execute();
    };

    public static TasksPagerFragment newInstance(int page) {
        TasksPagerFragment tasksPagerFragment = new TasksPagerFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        tasksPagerFragment.setArguments(arguments);
        return tasksPagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeLayout = view.findViewById(R.id.swipe_layout);
        RecyclerView tasksList = view.findViewById(R.id.tasks_list);
        tasksList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        if (pageNumber == 0) {
            tasksList.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new TasksAdapter(tasksManager.getNotDoneTasks(), notDoneClickListener);
            tasksList.setAdapter(adapter);
        } else if (pageNumber == 1) {
            tasksList.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new TasksAdapter(tasksManager.getDoneTasks(), doneClickListener);
            tasksList.setAdapter(adapter);
        }

        swipeLayout.setOnRefreshListener(() -> presenter.updateTasks());
    }


    @Override
    public void setListToAdapter(List<Task> listToAdapter) {
        tasksManager.updateDoneNotDone();
        adapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
        AuthChecker.checkAuth(getActivity());
        AuthChecker.checkServerErrorRedirectLoginActivity(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((TasksPagerComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new TasksPagerModule(this))).inject(this);
        return inflater.inflate(R.layout.tasks_fragment, container, false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

}
