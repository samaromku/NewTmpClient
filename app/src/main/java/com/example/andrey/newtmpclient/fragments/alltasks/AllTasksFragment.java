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
import android.util.Log;
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
import com.example.andrey.newtmpclient.activities.taskactivity.OneTaskActivity;
import com.example.andrey.newtmpclient.adapter.TasksAdapter;
import com.example.andrey.newtmpclient.base.BaseFragment;
import com.example.andrey.newtmpclient.dialogs.filter.FilterDialog;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.fragments.alltasks.di.DoneTasksComponent;
import com.example.andrey.newtmpclient.fragments.alltasks.di.DoneTasksModule;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.AuthChecker;
import com.example.andrey.newtmpclient.storage.ConverterMessages;
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

import static com.example.andrey.newtmpclient.storage.Const.ALL_TIME;
import static com.example.andrey.newtmpclient.storage.Const.DAY;
import static com.example.andrey.newtmpclient.storage.Const.MONTH;
import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;
import static com.example.andrey.newtmpclient.storage.Const.TASK_NUMBER;
import static com.example.andrey.newtmpclient.storage.Const.WEEK;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etSearch)
    protected EditText etSearch;

    @OnClick(R.id.ivClose)
    void onCloseClick() {
        etSearch.setText("");
    }

    @OnClick(R.id.ivBack)
    void onBackClick() {
        backClick();
    }

    @BindString(R.string.current_tasks)
    String currentTasks;
    @BindString(R.string.done_tasks)
    String doneTasks;
    @BindString(R.string.one_day)String oneDay;
    @BindString(R.string.one_week)String oneWeek;
    @BindString(R.string.one_month)String oneMonth;
    @BindString(R.string.all_period)String allTime;

    protected SwipeRefreshLayout swipeLayout;
    protected RecyclerView tasksList;
    protected TasksAdapter adapter;
    private boolean done;
    private UserRolesManager userRolesManager = UserRolesManager.INSTANCE;
    private UsersManager usersManager = UsersManager.INSTANCE;
    @Inject
    AllTasksPresenter presenter;

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public void startCreateTaskActivity(int taskId) {
        startActivity(new Intent(getActivity(), OneTaskActivity.class)
                .putExtra(TASK_NUMBER, taskId));
    }

    private void backClick() {
        toolbar.setVisibility(View.VISIBLE);
        searchToolbar.setVisibility(View.GONE);
        hideKeyboard(getActivity(), etSearch);
    }

    @Override
    public void startCreateTaskActivity() {
        startActivity(new Intent(getActivity(), CreateTaskActivity.class));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DoneTasksComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new DoneTasksModule(this))).inject(this);
        ButterKnife.bind(this, getActivity());
        setDialogTitleAndText("Получаем комментарии", PLEASE_WAIT);
        if (done) {
            adapter = new TasksAdapter(new ArrayList<>(), (v, position) ->
                    presenter.getComments(position));
            setToolbarTitle(doneTasks);
        } else {
            adapter = new TasksAdapter(new ArrayList<>(), (v, position) ->
                    presenter.getComments(position));
            setToolbarTitle(currentTasks);
        }
        return inflater.inflate(R.layout.fragment_tasks, container, false);
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
        presenter.getFirstAddresses();
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
            case R.id.action_filter:
                openFilterDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openFilterDialog() {
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.setOnDialogClosed(data -> {
            if(oneDay.equals(data)){
                presenter.getTasksByFilter(DAY, done);
            }else if(oneWeek.equals(data)){
                presenter.getTasksByFilter(WEEK, done);
            }else if(oneMonth.equals(data)){
                presenter.getTasksByFilter(MONTH, done);
            }else if(allTime.equals(data)){
                presenter.getTasksByFilter(ALL_TIME, done);
            }
            Log.i(TAG, "openFilterDialog: " + data);
        });
        filterDialog.show(getFragmentManager(), "filter");
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

    public void onSearch(String search) {
        presenter.getSearchedList(search, done);
    }
}
