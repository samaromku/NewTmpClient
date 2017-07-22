package com.example.andrey.newtmpclient.fragments.task_pager_fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.taskactivity.TaskActivity;
import com.example.andrey.newtmpclient.adapter.TasksAdapter;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.AuthChecker;
import com.example.andrey.newtmpclient.storage.ConverterMessages;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;
import com.example.andrey.newtmpclient.storage.Updater;

public class TasksPageFragment extends Fragment {
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    int pageNumber;
    private TasksAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private ConverterMessages converter = new ConverterMessages();
    private UsersManager usersManager = UsersManager.INSTANCE;

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

    public static TasksPageFragment newInstance(int page){
        TasksPageFragment ScreenSlidePageFragment = new TasksPageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        ScreenSlidePageFragment.setArguments(arguments);
        return ScreenSlidePageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.tasks_fragment, container, false);

        RecyclerView tasksList = (RecyclerView) rootView.findViewById(R.id.tasks_list);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout);


        if(pageNumber==0){
            tasksList.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new TasksAdapter(tasksManager.getNotDoneTasks(), notDoneClickListener);
            tasksList.setAdapter(adapter);
        }else if(pageNumber==1){
            tasksList.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new TasksAdapter(tasksManager.getDoneTasks(), doneClickListener);
            tasksList.setAdapter(adapter);
        }
        swipeLayout.setOnRefreshListener(() -> new UpdateDataSwip().execute());
        return rootView;
    }

    private class UpdateDataSwip extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeLayout.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            User user = usersManager.getUser();
            Request request = new Request(user, Request.UPDATE_TASKS);
            converter.sendMessageToServer(request);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tasksManager.updateDoneNotDone();
            adapter.notifyDataSetChanged();
            swipeLayout.setRefreshing(false);
            AuthChecker.checkAuth(getActivity());
            AuthChecker.checkServerErrorRedirectLoginActivity(getActivity());
        }
    }
}