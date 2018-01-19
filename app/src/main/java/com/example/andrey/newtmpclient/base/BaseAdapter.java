package com.example.andrey.newtmpclient.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.andrey.newtmpclient.interfaces.OnItemClickListener;

import java.util.List;


/**
 * Created by Andrey on 25.09.2017.
 */

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    private List<T> dataList;
    private OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        holder.bind(dataList.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
