package com.example.andrey.newtmpclient.activities.address;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.address.di.AddressMvpComponent;
import com.example.andrey.newtmpclient.activities.address.di.AddressMvpModule;

import android.support.v7.widget.RecyclerView;

import com.example.andrey.newtmpclient.base.BaseFragment;
import com.example.andrey.newtmpclient.entities.Address;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.support.v7.widget.LinearLayoutManager;

import com.example.andrey.newtmpclient.activities.address.adapter.AddressAdapter;

import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

public class AddressMvpFragment extends BaseFragment implements AddressMvpView {
    private static final String TAG = AddressMvpFragment.class.getSimpleName();
    public static final String UPLOAD_FILE = "Загрузка";
    public static final String PLEASE_WAIT = "Пожалуйста подождите";

    @Inject
    AddressMvpPresenter presenter;
    private ProgressDialog mDialog;
    private AddressAdapter adapter;

    @BindView(R.id.rvAddress)
    RecyclerView rvAddress;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle("Адреса");
        ((AddressMvpComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new AddressMvpModule(this))).inject(this);
        ButterKnife.bind(this, view);
        mDialog = new ProgressDialog(getActivity());
        mDialog.setCancelable(false);
        mDialog.setMessage(UPLOAD_FILE);
        mDialog.setTitle(PLEASE_WAIT);
        presenter.getListFroAdapter();
    }

    @Override
    public void showDialog() {
        mDialog.show();
    }

    @Override
    public void hideDialog() {
        mDialog.dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.address_activity, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

    //
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(this, CreateTaskActivity.class));
//    }

    @Override
    public void setListToAdapter(List<Address> listToAdapter) {
        adapter = new AddressAdapter();
        adapter.setDataList(listToAdapter);
        adapter.setClickListener(position -> {
            Log.i(TAG, "setListToAdapter: " + position);
        });
        rvAddress.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAddress.setAdapter(adapter);
    }
}
