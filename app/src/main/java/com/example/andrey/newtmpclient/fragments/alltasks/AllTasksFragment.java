package com.example.andrey.newtmpclient.fragments.alltasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.createTask.CreateTaskActivity;
import com.example.andrey.newtmpclient.adapter.TasksAdapter;
import com.example.andrey.newtmpclient.base.BaseFragment;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.fragments.alltasks.di.DoneTasksComponent;
import com.example.andrey.newtmpclient.fragments.alltasks.di.DoneTasksModule;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.AuthChecker;
import com.example.andrey.newtmpclient.storage.ConverterMessages;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;
import com.example.andrey.newtmpclient.storage.Updater;
import com.example.andrey.newtmpclient.taskactivity.TaskActivity;
import com.example.andrey.newtmpclient.utils.Const;
import com.google.firebase.iid.FirebaseInstanceId;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;
import static com.example.andrey.newtmpclient.utils.Utils.hideKeyboard;
import static com.example.andrey.newtmpclient.utils.Utils.showKeyboard;

/**
 * Created by savchenko on 22.01.18.
 */

public class AllTasksFragment extends BaseFragment implements
        AllTasksView {
    public static final String TAG = "AllTasksFragment";
    @BindView(R.id.search_toolbar)
    Toolbar searchToolbar;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.etSearch)protected EditText etSearch;
    @OnClick(R.id.ivClose)
    void onCloseClick(){
        etSearch.setText("");
    }
    @OnClick(R.id.ivBack)
    void onBackClick(){
        backClick();
    }
    @BindString(R.string.current_tasks)
    String currentTasks;
    @BindString(R.string.done_tasks)
    String doneTasks;
    protected SwipeRefreshLayout swipeLayout;
    protected RecyclerView tasksList;
    protected TasksAdapter adapter;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private boolean done;
    private UserRolesManager userRolesManager = UserRolesManager.INSTANCE;
    private Client client = Client.INSTANCE;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private AddressManager addressManager = AddressManager.INSTANCE;
    @Inject
    AllTasksPresenter presenter;

    public void setDone(boolean done) {
        this.done = done;
    }

    private OnListItemClickListener doneClickListener = (v, position) -> {
        Task task = tasksManager.getDoneTasks().get(position);
//        Intent intent = new Intent(getActivity(), TaskActivity.class).putExtra("taskNumber", task.getId());
//        new Updater(getActivity(), new Request(task, Request.WANT_SOME_COMMENTS), intent).execute();
        presenter.getComments(task);
    };

    private OnListItemClickListener notDoneClickListener = (v, position) -> {
        Task task = tasksManager.getNotDoneTasks().get(position);
//        Intent intent = new Intent(getActivity(), TaskActivity.class).putExtra("taskNumber", task.getId());
//        new Updater(getActivity(), new Request(task, Request.WANT_SOME_COMMENTS), intent).execute();
        presenter.getComments(task);
    };

    @Override
    public void startTaskActivity(int taskId) {
        startActivity(new Intent(getActivity(), TaskActivity.class)
                .putExtra("taskNumber", taskId));
    }

    private void backClick(){
        toolbar.setVisibility(View.VISIBLE);
        searchToolbar.setVisibility(View.GONE);
        hideKeyboard(getActivity(), etSearch);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DoneTasksComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new DoneTasksModule(this))).inject(this);
        ButterKnife.bind(this, getActivity());
        setDialogTitleAndText("Получаем комментарии", PLEASE_WAIT);
        if(done) {
            adapter = new TasksAdapter(new ArrayList<>(), doneClickListener);
            setToolbarTitle(doneTasks);
        }else {
            adapter = new TasksAdapter(new ArrayList<>(), notDoneClickListener);
            setToolbarTitle(currentTasks);
        }
        return inflater.inflate(R.layout.tasks_fragment, container, false);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
        App.getComponentManager().releaseComponent(getClass());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        RxTextView.textChanges(etSearch)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> onSearch(text.toString()));
        swipeLayout = view.findViewById(R.id.swipe_layout);
        tasksList = view.findViewById(R.id.tasks_list);
        tasksList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        addFireBaseTokenIfFromAuth();
        buttonAddTask(view);
        swipeLayout.setOnRefreshListener(() -> {
            swipeLayout.setRefreshing(true);
            presenter.updateTasks(done);
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_drawer, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                openToolbarSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openToolbarSearch() {
        toolbar.setVisibility(View.GONE);
        searchToolbar.setVisibility(View.VISIBLE);
        showKeyboard(getActivity(), etSearch);
    }

    @Override
    public void setListToAdapter(List<Task> listToAdapter) {
        adapter.setTasks(listToAdapter);
        tasksList.setLayoutManager(new LinearLayoutManager(getActivity()));
        tasksList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
        AuthChecker.checkAuth(getActivity());
    }

    public void onSearch(String search){
        presenter.getSearchedList(search, done);
    }
}
