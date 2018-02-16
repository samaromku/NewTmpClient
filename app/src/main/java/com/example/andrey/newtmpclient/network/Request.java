package com.example.andrey.newtmpclient.network;

import com.example.andrey.newtmpclient.entities.Comment;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.entities.UserCoords;
import com.example.andrey.newtmpclient.entities.UserRole;
import com.example.andrey.newtmpclient.managers.TokenManager;

import java.util.Date;

public class Request {
    private String request;
    private User user;
    private Task task;
    private UserRole userRole;
    private Comment comment;
    private UserCoords userCoords;
    private Token token;
    private String fireBase;
    private int userId;
    private Date userCoordesDate;
    public static final String ADD_TASK_TO_SERVER = "add_task_to_server";
    public static final String WANT_SOME_COMMENTS = "give_me_comments_by_task_id";
    public static final String CHANGE_PERMISSION_PLEASE = "change_permission_please";
    public static final String GIVE_ME_ADDRESSES_PLEASE = "give_me_addresses_please";
    public static final String ADD_NEW_USER = "add_new_user";
    public static final String ADD_NEW_ROLE = "add_new_role";
    public static final String ADD_COORDS = "add_coords";
    public static final String UPDATE_TASK = "update_task";
    public static final String GIVE_ME_LAST_USERS_COORDS = "give_me_last_users_coords";
    public static final String REMOVE_TASK = "remove_task";
    public static final String REMOVE_USER = "remove_user";
    public static final String AUTH = "auth";
    public static final String LOGOUT = "logout";
    public static final String UPDATE_TASKS = "update_tasks";
    public static final String GET_DONE_TASKS = "get_done_tasks";
    public static final String GET_NOT_DONE_TASKS = "get_not_done_tasks";
    public static final String ADD_FIREBASE_TOKEN = "add_firebase_token";
    public static final String GET_USER_COORDES_PER_DAY = "get_user_coordes_per_day";

    public void setToken(Token token) {
        this.token = token;
    }

    public Request(Task task, String request){
        this.task = task;
        this.request = request;
    }

    public Request(UserCoords userCoords, String request){
        this.userCoords = userCoords;
        this.request = request;
    }

    public Request(Comment comment, String request){
        this.comment = comment;
        this.request = request;
    }

    public Request(String request){
        this.request = request;
    }

    public static Request requestWithToken(String request){
        Request request1 = new Request();
        request1.setRequest(request);
        request1.setToken(TokenManager.instance.getToken());
        return request1;
    }


    public static Request requestUserWithToken(User user, String request){
        Request request1 = new Request();
        request1.setRequest(request);
        request1.user = user;
        request1.setToken(TokenManager.instance.getToken());
        return request1;
    }

    public static Request requestUserIdWithDateWithToken(int userId, Date date, String request){
        Request request1 = new Request();
        request1.setRequest(request);
        request1.userId = userId;
        request1.userCoordesDate = date;
        request1.setToken(TokenManager.instance.getToken());
        return request1;
    }

    public static Request requestCommentWithToken(Comment comment, String request){
        Request request1 = new Request();
        request1.setRequest(request);
        request1.comment = comment;
        request1.setToken(TokenManager.instance.getToken());
        return request1;
    }

    public static Request requestUserRoleWithToken(UserRole userRole, String request){
        Request request1 = new Request();
        request1.setRequest(request);
        request1.userRole = userRole;
        request1.setToken(TokenManager.instance.getToken());
        return request1;
    }

    public static Request requestTaskWithToken(Task task, String request){
        Request request1 = new Request();
        request1.setRequest(request);
        request1.task = task;
        request1.setToken(TokenManager.instance.getToken());
        return request1;
    }

    private Request(){}

    private static final Request instance = new Request();

    public static Request getInstance(){
        return instance;
    }

    public static Request addFireBase(String request, int userId, String fireBase){
        getInstance().request = request;
        getInstance().userId = userId;
        getInstance().fireBase = fireBase;
        return getInstance();
    }

    public Request(User user, String request){
        this.user = user;
        this.request = request;
    }

    public Request(UserRole userRole, String request){
        this.userRole = userRole;
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
