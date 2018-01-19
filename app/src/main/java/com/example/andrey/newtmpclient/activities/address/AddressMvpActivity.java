package com.example.andrey.newtmpclient.activities.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.address.di.AddressMvpComponent;
import com.example.andrey.newtmpclient.activities.address.di.AddressMvpModule;

import android.support.v7.widget.RecyclerView;

import com.example.andrey.newtmpclient.base.BaseActivity;
import com.example.andrey.newtmpclient.createTask.CreateTaskActivity;
import com.example.andrey.newtmpclient.entities.Address;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.support.v7.widget.LinearLayoutManager;

import com.example.andrey.newtmpclient.activities.address.adapter.AddressAdapter;

import java.util.List;

import android.util.Log;

import javax.inject.Inject;

public class AddressMvpActivity extends BaseActivity implements AddressMvpView {
    private static final String TAG = AddressMvpActivity.class.getSimpleName();
    @Inject
    AddressMvpPresenter presenter;

    @BindView(R.id.rvAddress)
    RecyclerView rvAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_activity);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Адреса");
        }
        ((AddressMvpComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new AddressMvpModule(this))).inject(this);
        ButterKnife.bind(this);
        presenter.getListFroAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            App.getComponentManager().releaseComponent(getClass());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, CreateTaskActivity.class));
    }

    @Override
    public void setListToAdapter(List<Address> listToAdapter) {
        AddressAdapter adapter = new AddressAdapter();
        adapter.setDataList(listToAdapter);
        adapter.setClickListener(position -> {
            Log.i(TAG, "setListToAdapter: " + position);
        });
        rvAddress.setLayoutManager(new LinearLayoutManager(this));
        rvAddress.setAdapter(adapter);
    }
}
