package com.example.andrey.newtmpclient.fragments.alltasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.AccountActivity;
import com.example.andrey.newtmpclient.base.BaseFragment;
import com.example.andrey.newtmpclient.createTask.CreateTaskActivity;
import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.fragments.alltasks.di.AllTasksComponent;
import com.example.andrey.newtmpclient.fragments.alltasks.di.AllTasksModule;
import com.example.andrey.newtmpclient.fragments.task_pager_fragment.TasksPageFragment;
import com.example.andrey.newtmpclient.login.LoginActivity;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.TokenManager;
import com.example.andrey.newtmpclient.managers.UserRolesManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.storage.ConverterMessages;
import com.example.andrey.newtmpclient.storage.Updater;
import com.example.andrey.newtmpclient.utils.Const;
import com.google.firebase.iid.FirebaseInstanceId;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import javax.inject.Inject;

public class AllTasksFragment extends BaseFragment implements AllTasksView {
    private static final String TAG = AllTasksFragment.class.getSimpleName();
    @Inject
    AllTasksPresenter presenter;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private UserRolesManager userRolesManager = UserRolesManager.INSTANCE;
    private Client client = Client.INSTANCE;
    private TokenManager tokenManager = TokenManager.instance;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private AddressManager addressManager = AddressManager.INSTANCE;
    ViewPager pager;
    PagerAdapter pagerAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AllTasksComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new AllTasksModule(this))).inject(this);
        return inflater.inflate(R.layout.tasks_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(usersManager.getUser()!=null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Привет, " + usersManager.getUser().getLogin());

            pager = (ViewPager) view.findViewById(R.id.pager);
            pagerAdapter = new AllTasksFragment.ScreenSlidePagerAdapter(
                    getActivity().getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);

            addFireBaseTokenIfFromAuth();
            buttonAddTask(view);
        }else {
            Toast.makeText(getActivity(), "Вы не авторизованы", Toast.LENGTH_SHORT).show();
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
        if(!client.isAuth()){
            Log.i(TAG, "onResume: accountactivity " + client.isAuth());
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    private void addFireBaseTokenIfFromAuth(){
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
            return TasksPageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private void firstTimeAddAddresses(){
        Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
        if(addressManager.getAddresses().size()==0) {
            new Updater(getActivity(), new Request(Request.GIVE_ME_ADDRESSES_PLEASE), intent).execute();
        }else startActivity(intent);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        startActivity(intent);
//    }

    private void buttonAddTask(View view){
        //кнопка добавить задание
        FloatingActionButton addTask = (FloatingActionButton) view.findViewById(R.id.add_task_btn);
        UserRole userRole = userRolesManager.getUserRole();
        if(userRole!=null){
            if(userRole.isMakeTasks()) {
                addTask.setVisibility(View.VISIBLE);
            }
            else addTask.setVisibility(View.INVISIBLE);
        }
        addTask.setOnClickListener(v -> firstTimeAddAddresses());
    }

}
