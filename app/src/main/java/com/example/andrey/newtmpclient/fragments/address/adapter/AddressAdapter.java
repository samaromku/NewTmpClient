package com.example.andrey.newtmpclient.fragments.address.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.base.BaseAdapter;
import com.example.andrey.newtmpclient.base.BaseViewHolder;
import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.interfaces.OnItemClickListener;

public class AddressAdapter extends BaseAdapter<Address> {
    @Override
    public BaseViewHolder<Address> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    class AddressViewHolder extends BaseViewHolder<Address> {
        @BindView(R.id.address_id) TextView addressId;
        @BindView(R.id.org_name) TextView orgName;
        @BindView(R.id.address) TextView addressName;


        @Override
        public void bind(Address address, OnItemClickListener clickListener) {
            super.bind(address, clickListener);
            addressId.setText(String.valueOf(address.getId()));
            orgName.setText(address.getName());
            addressName.setText(address.getAddress());
        }

        AddressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
