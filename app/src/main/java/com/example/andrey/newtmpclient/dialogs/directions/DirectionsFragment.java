package com.example.andrey.newtmpclient.dialogs.directions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.dialogs.directions.adapter.UserAdapter;
import com.example.andrey.newtmpclient.dialogs.directions.di.DirectionsComponent;
import com.example.andrey.newtmpclient.dialogs.directions.di.DirectionsModule;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.interfaces.UserDateGetter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DirectionsFragment extends DialogFragment implements DirectionsView {
    private static final String TAG = DirectionsFragment.class.getSimpleName();
    private UserDateGetter userDateGetter;
    private UserAdapter adapter;
    private Date calDate;

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public void setUserDateGetter(UserDateGetter userDateGetter) {
        this.userDateGetter = userDateGetter;
    }

    @Inject
    DirectionsPresenter presenter;

    @OnClick(R.id.btnCancel)
    void onCancelClick() {
        hideDialog();
    }

    @OnClick(R.id.btnOk)
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(calDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dpDirectionsDate.init(year, month, day, null);
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
        Calendar cal = new GregorianCalendar(year1, month1, day1);
        cal.set(Calendar.AM_PM, Calendar.AM);
        Date date1 = cal.getTime();
        userDateGetter.onUserDateGet(user, date1);
    }

    @Override
    public void updateAdapterPosition(int position) {
        adapter.notifyDataSetChanged();
    }
}
