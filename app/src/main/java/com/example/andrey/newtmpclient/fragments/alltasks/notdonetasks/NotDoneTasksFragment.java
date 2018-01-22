package com.example.andrey.newtmpclient.fragments.alltasks.notdonetasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.createTask.CreateTaskActivity;
import com.example.andrey.newtmpclient.activities.login.LoginActivity;
import com.example.andrey.newtmpclient.adapter.TasksAdapter;
import com.example.andrey.newtmpclient.base.BaseSearchFragment;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.fragments.alltasks.donetasks.DoneTasksPresenter;
import com.example.andrey.newtmpclient.fragments.alltasks.notdonetasks.di.NotDoneTasksComponent;
import com.example.andrey.newtmpclient.fragments.alltasks.notdonetasks.di.NotDoneTasksModule;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.ConverterMessages;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;
import com.example.andrey.newtmpclient.storage.Updater;
import com.example.andrey.newtmpclient.taskactivity.TaskActivity;
import com.example.andrey.newtmpclient.utils.Const;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.ButterKnife;

public class NotDoneTasksFragment extends BaseSearchFragment implements NotDoneTasksView {
    private static final String TAG = NotDoneTasksFragment.class.getSimpleName();
    @Inject
    DoneTasksPresenter presenter;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    @BindString(R.string.current_tasks)
    String currentTasks;
    private UserRolesManager userRolesManager = UserRolesManager.INSTANCE;
    private Client client = Client.INSTANCE;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private AddressManager addressManager = AddressManager.INSTANCE;

    private OnListItemClickListener notDoneClickListener = (v, position) -> {
        Task task = tasksManager.getNotDoneTasks().get(position);
        Intent intent = new Intent(getActivity(), TaskActivity.class).putExtra("taskNumber", task.getId());
        new Updater(getActivity(), new Request(task, Request.WANT_SOME_COMMENTS), intent).execute();
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle(currentTasks);

        addFireBaseTokenIfFromAuth();
        buttonAddTask(view);
        swipeLayout.setOnRefreshListener(() -> {
            swipeLayout.setRefreshing(true);
            presenter.updateTasks(false);
        });
    }

    @Override
    public void onSearch(String search) {
        presenter.getSearchedList(search, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((NotDoneTasksComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new NotDoneTasksModule(this))).inject(this);
        adapter = new TasksAdapter(new ArrayList<>(), notDoneClickListener);
        ButterKnife.bind(this, getActivity());
        return inflater.inflate(R.layout.tasks_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!client.isAuth()) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

    private void addFireBaseTokenIfFromAuth() {
        if (FirebaseInstanceId.getInstance().getToken() != null) {
            ConverterMessages converter = new ConverterMessages();
            new Thread(() -> {
                if (getActivity().getIntent().getBooleanExtra(Const.FROM_AUTH, false)) {
                    converter.sendMessageToServer(Request.addFireBase(Request.ADD_FIREBASE_TOKEN, usersManager.getUser(), FirebaseInstanceId.getInstance().getToken()));
                }
            }).start();
        }

    }

    private void buttonAddTask(View view) {
        //кнопка добавить задание
        FloatingActionButton addTask = view.findViewById(R.id.add_task_btn);
        UserRole userRole = userRolesManager.getUserRole();
        if (userRole != null) {
            if (userRole.isMakeTasks()) {
                addTask.setVisibility(View.VISIBLE);
            } else addTask.setVisibility(View.INVISIBLE);
        }
        addTask.setOnClickListener(v -> firstTimeAddAddresses());
    }

    private void firstTimeAddAddresses() {
        Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
        if (addressManager.getAddresses().size() == 0) {
            new Updater(getActivity(), new Request(Request.GIVE_ME_ADDRESSES_PLEASE), intent).execute();
        } else startActivity(intent);
    }

}
