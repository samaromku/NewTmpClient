package com.example.andrey.newtmpclient.dialogs.directions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.dialogs.directions.di.DirectionsComponent;
import com.example.andrey.newtmpclient.dialogs.directions.di.DirectionsModule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.RecyclerView;

import com.example.andrey.newtmpclient.entities.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.support.v7.widget.LinearLayoutManager;

import com.example.andrey.newtmpclient.dialogs.directions.adapter.UserAdapter;
import com.example.andrey.newtmpclient.interfaces.UserDateGetter;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.util.Log;
import android.widget.DatePicker;

import javax.inject.Inject;

public class DirectionsFragment extends DialogFragment implements DirectionsView {
    private static final String TAG = DirectionsFragment.class.getSimpleName();
    private UserDateGetter userDateGetter;
    private UserAdapter adapter;

    public void setUserDateGetter(UserDateGetter userDateGetter) {
        this.userDateGetter = userDateGetter;
    }

    @Inject
    DirectionsPresenter presenter;

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        hideDialog();
    }

    @OnClick(R.id.btn_ok)
    void onOkClick() {
        presenter.getSelected();
    }

    @BindView(R.id.rvUser)
    RecyclerView rvUser;
    @BindView(R.id.dp_directions_date)
    DatePicker dpDirectionsDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DirectionsComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new DirectionsModule(this))).inject(this);
        return inflater.inflate(R.layout.fragment_directions, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.getListFroAdapter();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

    @Override
    public void setListToAdapter(List<User> listToAdapter) {
        adapter = new UserAdapter();
        adapter.setDataList(listToAdapter);
        adapter.setClickListener(position -> {
            presenter.onUserClick(position);
        });
        rvUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvUser.setAdapter(adapter);
    }

    @Override
    public void hideDialog() {
        getDialog().dismiss();
    }

    @Override
    public void setUserDateToActivity(User user) {
        int year1 = dpDirectionsDate.getYear();
        int month1 = dpDirectionsDate.getMonth();
        int day1 = dpDirectionsDate.getDayOfMonth();
        Date date1 = new GregorianCalendar(year1, month1, day1).getTime();
        userDateGetter.onUserDateGet(user, date1);
    }

    @Override
    public void updateAdapterPosition(int position) {
        adapter.notifyDataSetChanged();
    }
}
