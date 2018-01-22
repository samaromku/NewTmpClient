package com.example.andrey.newtmpclient.fragments.alltasks.donetasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.adapter.TasksAdapter;
import com.example.andrey.newtmpclient.base.BaseSearchFragment;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.fragments.alltasks.donetasks.di.DoneTasksComponent;
import com.example.andrey.newtmpclient.fragments.alltasks.donetasks.di.DoneTasksModule;
import com.example.andrey.newtmpclient.interfaces.OnItemClickListener;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;
import com.example.andrey.newtmpclient.storage.Updater;
import com.example.andrey.newtmpclient.taskactivity.TaskActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.ButterKnife;

public class DoneTasksFragment extends BaseSearchFragment implements
        DoneTasksView,
        OnItemClickListener{
    private static final String TAG = DoneTasksFragment.class.getSimpleName();
    @Inject
    DoneTasksPresenter presenter;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    @BindString(R.string.done_tasks)
    String doneTasks;

    @Override
    public void onSearch(String search) {
        presenter.getSearchedList(search, true);
    }

    @Override
    public void onClick(int position) {
        Task task = tasksManager.getDoneTasks().get(position);
        Intent intent = new Intent(getActivity(), TaskActivity.class).putExtra("taskNumber", task.getId());
        new Updater(getActivity(), new Request(task, Request.WANT_SOME_COMMENTS), intent).execute();
    }

    private OnListItemClickListener doneClickListener = (v, position) -> {
        Task task = tasksManager.getDoneTasks().get(position);
        Intent intent = new Intent(getActivity(), TaskActivity.class).putExtra("taskNumber", task.getId());
        new Updater(getActivity(), new Request(task, Request.WANT_SOME_COMMENTS), intent).execute();
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle(doneTasks);

        swipeLayout.setOnRefreshListener(() -> {
            swipeLayout.setRefreshing(true);
            presenter.updateTasks(true);
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DoneTasksComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new DoneTasksModule(this))).inject(this);
        ButterKnife.bind(this, getActivity());
        adapter = new TasksAdapter(new ArrayList<>(), doneClickListener);
        return inflater.inflate(R.layout.tasks_fragment, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

}
