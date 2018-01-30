package com.example.andrey.newtmpclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> users;
    private OnListItemClickListener clickListener;

    public UserAdapter(List<User> users, OnListItemClickListener clickListener) {
        this.users = users;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(users.get(position));
    }



    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userId;
        TextView fio;


        public ViewHolder(View itemView) {
            super(itemView);
//            userId = (TextView) itemView.findViewById(R.id.users_id);
            fio = (TextView) itemView.findViewById(R.id.fio);
            itemView.setOnClickListener(this);
        }

        public void bind(User user) {
//            char[]letters = user.getRole().toCharArray();
//            userId.setText(String.valueOf(letters[0]).toUpperCase());
            fio.setText(user.getFIO());
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}