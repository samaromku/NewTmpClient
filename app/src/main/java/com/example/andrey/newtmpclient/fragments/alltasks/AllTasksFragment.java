package com.example.andrey.newtmpclient.fragments.alltasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.base.BaseFragment;
import com.example.andrey.newtmpclient.activities.createTask.CreateTaskActivity;
import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.fragments.alltasks.di.AllTasksComponent;
import com.example.andrey.newtmpclient.fragments.alltasks.di.AllTasksModule;
import com.example.andrey.newtmpclient.fragments.task_pager_fragment.TasksPagerFragment;
import com.example.andrey.newtmpclient.activities.login.LoginActivity;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.ConverterMessages;
import com.example.andrey.newtmpclient.storage.Updater;
import com.example.andrey.newtmpclient.utils.Const;
import com.google.firebase.iid.FirebaseInstanceId;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.andrey.newtmpclient.storage.Const.NOT_AUTH;

public class AllTasksFragment extends BaseFragment implements AllTasksView {
    private static final String TAG = AllTasksFragment.class.getSimpleName();
    @Inject
    AllTasksPresenter presenter;
    private UserRolesManager userRolesManager = UserRolesManager.INSTANCE;
    private Client client = Client.INSTANCE;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private AddressManager addressManager = AddressManager.INSTANCE;
    @BindString(R.string.current_tasks)
    String currentTasks;
    @BindString(R.string.done_tasks)
    String doneTasks;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AllTasksComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new AllTasksModule(this))).inject(this);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.tasks_view_pager, container, false);
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
                Log.i(TAG, "onOptionsItemSelected: search");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (usersManager.getUser() != null) {
            setToolbarTitle(currentTasks);

            ViewPager pager = view.findViewById(R.id.pager);
            PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(
                    getActivity().getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        setToolbarTitle(currentTasks);
                    } else if (position == 1) {
                        setToolbarTitle(doneTasks);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            addFireBaseTokenIfFromAuth();
            buttonAddTask(view);
        } else {
            Toast.makeText(getActivity(), NOT_AUTH, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!client.isAuth()) {
            Log.i(TAG, "onResume: accountactivity " + client.isAuth());
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
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

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TasksPagerFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private void firstTimeAddAddresses() {
        Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
        if (addressManager.getAddresses().size() == 0) {
            new Updater(getActivity(), new Request(Request.GIVE_ME_ADDRESSES_PLEASE), intent).execute();
        } else startActivity(intent);
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

}
