package com.example.andrey.newtmpclient.fragments.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.fragments.address.adapter.AddressAdapter;
import com.example.andrey.newtmpclient.fragments.address.di.AddressMvpComponent;
import com.example.andrey.newtmpclient.fragments.address.di.AddressMvpModule;
import com.example.andrey.newtmpclient.base.BaseFragment;
import com.example.andrey.newtmpclient.entities.Address;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.andrey.newtmpclient.storage.Const.PLEASE_WAIT;
import static com.example.andrey.newtmpclient.storage.Const.UPLOAD_FILE;

public class AddressMvpFragment extends BaseFragment implements AddressMvpView {
    private static final String TAG = AddressMvpFragment.class.getSimpleName();

    @Inject
    AddressMvpPresenter presenter;

    @BindView(R.id.rvAddress)
    RecyclerView rvAddress;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle("Адреса");
        ((AddressMvpComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new AddressMvpModule(this))).inject(this);
        ButterKnife.bind(this, view);
        setDialogTitleAndText(PLEASE_WAIT, UPLOAD_FILE);
        presenter.getListFroAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_address, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        App.getComponentManager().releaseComponent(getClass());
    }

    @Override
    public void setListToAdapter(List<Address> listToAdapter) {
        AddressAdapter adapter = new AddressAdapter();
        adapter.setDataList(listToAdapter);
        adapter.setClickListener(position -> Log.i(TAG, "setListToAdapter: " + position));
        rvAddress.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAddress.setAdapter(adapter);
        rvAddress.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
}
