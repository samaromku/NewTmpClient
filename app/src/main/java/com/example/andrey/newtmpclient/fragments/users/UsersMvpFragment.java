package com.example.andrey.newtmpclient.fragments.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.CreateUserActivity;
import com.example.andrey.newtmpclient.activities.oneuser.OneUserActivity;
import com.example.andrey.newtmpclient.base.BaseFragment;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.fragments.users.adapter.UserAdapter;
import com.example.andrey.newtmpclient.fragments.users.di.UsersMvpComponent;
import com.example.andrey.newtmpclient.fragments.users.di.UsersMvpModule;
import com.example.andrey.newtmpclient.managers.UserRolesManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersMvpFragment extends BaseFragment implements UsersMvpView {
    private static final String TAG = UsersMvpFragment.class.getSimpleName();
    @Inject
    UsersMvpPresenter presenter;
    @BindView(R.id.add_user) FloatingActionButton addUser;
    UserRolesManager userRolesManager = UserRolesManager.INSTANCE;

    @BindView(R.id.rvUsers)
    RecyclerView rvUser;
    private UserAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((UsersMvpComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new UsersMvpModule(this))).inject(this);
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.getListFroAdapter();
        setToolbarTitle("Пользователи");
        adminAction(view);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onStop() {
        super.onStop();
        App.getComponentManager().releaseComponent(getClass());
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setListToAdapter(List<User> listToAdapter) {
        adapter = new UserAdapter();
        adapter.setDataList(listToAdapter);
        adapter.setClickListener(position ->
                startActivity(new Intent(getActivity(), OneUserActivity.class)
                .putExtra("position", position)));
        rvUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvUser.setAdapter(adapter);
    }

    private void adminAction(View view){
        UserRole userRole = userRolesManager.getUserRole();
        //настройка видимости кнопки
        if(userRole!=null && userRole.isMakeNewUser()){
            addUser.setVisibility(View.VISIBLE);
        }else addUser.setVisibility(View.INVISIBLE);
        addUser.setOnClickListener(v -> startActivity(new Intent(getActivity(), CreateUserActivity.class)));
    }
}
