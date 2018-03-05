package com.example.andrey.newtmpclient.managers;

import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.entities.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TasksManager {
    private Task task;
    private List<Task> tasks;
    private List<Task> doneTasks;
    private List<Task> notDoneTasks;
    private List<Task> needDoingTasks;
    private Task removeTask;
    private String status;
    private String[] importanceString = new String[]{TaskEnum.STANDART, TaskEnum.INFO, TaskEnum.AVARY, TaskEnum.TIME};
    private String[] type = new String[]{TaskEnum.TAKE_INFO, TaskEnum.ARTF, TaskEnum.UUTE, TaskEnum.ITP, TaskEnum.INSPECTION, TaskEnum.APARTMENT};
    private String[] AllStatuses = new String[]{TaskEnum.NEW_TASK, TaskEnum.DISTRIBUTED_TASK, TaskEnum.DOING_TASK, TaskEnum.CONTROL_TASK, TaskEnum.DONE_TASK, TaskEnum.NEED_HELP};
    public static final TasksManager INSTANCE = new TasksManager();

    public Task getRemoveTask() {
        return removeTask;
    }

    public void setRemoveTask(Task removeTask) {
        this.removeTask = removeTask;
    }

    public String[] getAllStatuses() {
        return AllStatuses;
    }

    public String[] getType() {
        return type;
    }

    public String[] getImportanceString() {
        return importanceString;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Task getTask() {
        return task;
    }

    public List<Task>usersTask(User user){
        List<Task>usersTasks = new ArrayList<>();
        for(Task t:notDoneTasks){
            if(user!=null){
                if(t.getUserId()==user.getId() && t.getStatus().equals(TaskEnum.DISTRIBUTED_TASK)){
                    usersTasks.add(t);
                }
            }
        }
        return usersTasks;
    }

    public void setDoneTasks(List<Task> doneTasks) {
        tasks.clear();
        tasks.addAll(doneTasks);
    }

    public void setNotDoneTasks(List<Task> notDoneTasks) {
        tasks.clear();
        tasks.addAll(notDoneTasks);
    }

    public List<Task> getNotDoneTasks() {
        notDoneTasks.clear();
        for(Task t:tasks){
            if(!t.getStatus().equals(TaskEnum.DONE_TASK)) {
                notDoneTasks.add(t);
            }
        }
        return notDoneTasks;
    }


    public List<Task> getDoneTasks(){
        doneTasks.clear();
        for(Task t:tasks){
            if(t.getStatus().equals(TaskEnum.DONE_TASK)){
                doneTasks.add(t);
            }
        }
        return doneTasks;
    }

    public void updateDoneNotDone(){
        getDoneTasks();
        getNotDoneTasks();
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void addAll(List<Task> taskList){
        tasks.addAll(taskList);
    }

    public Task getById(int taskId){
        for(Task t:tasks){
            if(t.getId()==taskId){
                return t;
            }
        }
        return null;
    }

    public void removeDone(){
        Iterator<Task> iterator = tasks.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getStatus().equals(TaskEnum.DONE_TASK)){
                iterator.remove();
            }
        }
    }

    public void removeTask(Task task){
        Iterator<Task> iterator = tasks.iterator();
        while(iterator.hasNext()){
            if(iterator.next().equals(task)){
                iterator.remove();
            }
        }
    }

    public void updateTask(Task task){
        for(Task t:tasks){
            if(t.getId()==task.getId()){
                t.setAddress(task.getAddress());
                t.setOrgName(task.getOrgName());
                t.setAddressId(task.getAddressId());
                t.setBody(task.getBody());
                t.setDoneTime(task.getDoneTime());
                t.setImportance(task.getImportance());
                t.setStatus(task.getStatus());
                t.setType(task.getType());
                t.setUserId(task.getUserId());
            }
        }
        getDoneTasks();
        getNotDoneTasks();
    }

    public void addUnique(List<Task>taskList){
        for(Task t:taskList){
            if(!tasks.contains(t)){
                tasks.add(t);
            }
        }
    }

    public boolean isTaskInList(Task task){
        for(Task t:tasks){
            if(t.getId()==task.getId()){
                return true;
            }
        }
        return false;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void updateTask(String status, int id){
        for(Task t:tasks){
            if(t.getId()==id){
                t.setStatus(status);
            }
        }
    }

    public void removeAll(){
        if(tasks.size()>0){
            tasks.clear();
        }
    }

    public int getMaxId(){
        int max = 0;
        for(Task t:tasks){
            if(t.getId()>max){
                max=t.getId();
            }
        }
        return max;
    }

    public void addNeedDoing(Task task){
        boolean added = false;
        for(Task t:needDoingTasks){
            if(t.getId()==task.getId()){
                added = true;
            }
        }
        if(!added){
            needDoingTasks.add(task);
        }
    }

    public void removeDoing(){
        needDoingTasks.clear();
    }

    public List<Task> getNeedDoingTasks() {
        return needDoingTasks;
    }

    private TasksManager(){
        doneTasks = new ArrayList<>();
        notDoneTasks = new ArrayList<>();
        tasks = new ArrayList<>();
        needDoingTasks = new ArrayList<>();
    }
}

