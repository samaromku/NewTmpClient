package com.example.andrey.newtmpclient.dialogs.directions.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.base.BaseAdapter;
import com.example.andrey.newtmpclient.base.BaseViewHolder;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.interfaces.OnItemClickListener;

public class UserAdapter extends BaseAdapter<User> {
    @Override
    public BaseViewHolder<User> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    class UserViewHolder extends BaseViewHolder<User> {
        @BindView(R.id.users_id)TextView userId;
        @BindView(R.id.fio)TextView fio;
        @BindView(R.id.llUser)LinearLayout llUser;

        UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(User user, OnItemClickListener clickListener) {
            super.bind(user, clickListener);
            userId.setText(String.valueOf(user.getId()));
            fio.setText(user.getLogin());
            if(user.isSelected()){
                llUser.setBackgroundResource(R.color.colorAccent);
            }else {
                llUser.setBackgroundResource(R.color.white);
            }
        }
    }
}
