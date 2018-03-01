package com.example.andrey.newtmpclient.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;

import java.util.List;


public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private List<Task> tasks;
    private OnListItemClickListener clickListener;
    private UsersManager usersManager = UsersManager.INSTANCE;

    public TasksAdapter(List<Task> tasks, OnListItemClickListener clickListener) {
        this.tasks = tasks;
        this.clickListener = clickListener;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(tasks.get(position));
        if(tasks.get(position).getImportance()!=null){
            if(tasks.get(position).getStatus().equals(TaskEnum.DONE_TASK)){
                setColorsToItem(Color.LTGRAY,Color.BLACK,holder);
                return;
            }

            switch (tasks.get(position).getImportance()){
                case TaskEnum.STANDART:
                    setColorsToItem(Color.WHITE,Color.BLACK,holder);
                    break;

                case TaskEnum.AVARY:
                    setColorsToItem(Color.RED,Color.WHITE,holder);
                    break;

                case TaskEnum.INFO:
                    setColorsToItem(Color.BLUE,Color.WHITE,holder);
                    break;

                default:
                    setColorsToItem(Color.WHITE,Color.BLACK,holder);
                    break;
            }
        }
    }

    private void setColorsToItem(int backColor, int itemsColor, ViewHolder holder){
        holder.cvMain.setCardBackgroundColor(backColor);
        holder.title.setTextColor(itemsColor);
        holder.body.setTextColor(itemsColor);
        holder.address.setTextColor(itemsColor);
        holder.doneTime.setTextColor(itemsColor);
        holder.firsLetter.setTextColor(itemsColor);
        holder.userLogin.setTextColor(itemsColor);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView body;
        TextView address;
        TextView doneTime;
        TextView firsLetter;
        TextView userLogin;
        CardView cvMain;

        private ViewHolder(View itemView) {
            super(itemView);
            userLogin = itemView.findViewById(R.id.user_login);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            doneTime = itemView.findViewById(R.id.date);
            firsLetter = itemView.findViewById(R.id.firstLetter);
            body = itemView.findViewById(R.id.body);
            cvMain = itemView.findViewById(R.id.cvMain);
            itemView.setOnClickListener(this);
        }

        private void bind(Task task) {
            title.setText(task.getOrgName());
            address.setText(task.getAddress());
//                DateUtil dateUtil = new DateUtil();
//                String rightDate = dateUtil.convertDate(task.getDoneTime());
//                task.setDoneTime(rightDate);
            doneTime.setText("Выполнить до\n"+task.getDoneTime());
            body.setText(task.getBody());
            //заглушка на удаленного пользователя
            User userWithNotName = usersManager.getUserById(task.getUserId());
            if(userWithNotName!=null) {
                userLogin.setText(usersManager.getUserById(task.getUserId()).getLogin());
            }else userLogin.setText("Удален");
            char[] str = task.getStatus().toCharArray();
            /*Ставим плюсики и крестики*/
            switch (task.getStatus()) {
                case TaskEnum.NEED_HELP:
                    firsLetter.setText("+");
                    break;
                case TaskEnum.DONE_TASK:
                    firsLetter.setText("X");
                    break;
                default:
                    firsLetter.setText(String.valueOf(str[0]).toUpperCase());
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}

