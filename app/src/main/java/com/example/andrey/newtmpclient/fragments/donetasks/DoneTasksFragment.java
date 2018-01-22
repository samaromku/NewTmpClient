package com.example.andrey.newtmpclient.fragments.donetasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.adapter.TasksAdapter;
import com.example.andrey.newtmpclient.base.BaseFragment;
import com.example.andrey.newtmpclient.base.BaseSearchFragment;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.fragments.donetasks.di.DoneTasksComponent;
import com.example.andrey.newtmpclient.fragments.donetasks.di.DoneTasksModule;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.AuthChecker;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;
import com.example.andrey.newtmpclient.storage.Updater;
import com.example.andrey.newtmpclient.taskactivity.TaskActivity;
import com.jakewharton.rxbinding2.widget.RxTextView;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DoneTasksFragment extends BaseSearchFragment implements DoneTasksView {
    private static final String TAG = DoneTasksFragment.class.getSimpleName();
    @Inject
    DoneTasksPresenter presenter;
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
//    int pageNumber;
    private TasksAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    @BindString(R.string.done_tasks)
    String doneTasks;

    @Override
    public void onSearch(String search) {
        Log.i(TAG, "onSearch: " + search);
    }

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

    public static DoneTasksFragment newInstance(int page) {
        DoneTasksFragment tasksPagerFragment = new DoneTasksFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        tasksPagerFragment.setArguments(arguments);
        return tasksPagerFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getActivity());
        setToolbarTitle(doneTasks);
        swipeLayout = view.findViewById(R.id.swipe_layout);
        RecyclerView tasksList = view.findViewById(R.id.tasks_list);
        tasksList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        if(etSearch!=null) {
            RxTextView.textChanges(etSearch)
                    .debounce(1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(text -> {
                        presenter.getSearchedList(text.toString(), true);
                        Log.i(TAG, "onViewCreated: " + text);
                    });
        }
//        if (pageNumber == 0) {
//            tasksList.setLayoutManager(new LinearLayoutManager(getActivity()));
//            adapter = new TasksAdapter(tasksManager.getNotDoneTasks(), notDoneClickListener);
//            tasksList.setAdapter(adapter);
//        } else if (pageNumber == 1) {
            tasksList.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new TasksAdapter(tasksManager.getDoneTasks(), doneClickListener);
            tasksList.setAdapter(adapter);
//        }

        swipeLayout.setOnRefreshListener(() -> {
            swipeLayout.setRefreshing(true);
            presenter.updateTasks();
        });
    }

    @Override
    public void setListToAdapter(List<Task> listToAdapter) {
//        tasksManager.updateDoneNotDone();
        adapter.setTasks(listToAdapter);
        adapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
        AuthChecker.checkAuth(getActivity());
        AuthChecker.checkServerErrorRedirectLoginActivity(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DoneTasksComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new DoneTasksModule(this))).inject(this);
//        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        return inflater.inflate(R.layout.tasks_fragment, container, false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

}
