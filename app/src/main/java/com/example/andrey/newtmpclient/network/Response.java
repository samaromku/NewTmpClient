package com.example.andrey.newtmpclient.network;

import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.entities.Comment;
import com.example.andrey.newtmpclient.entities.ContactOnAddress;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.User;
import com.example.andrey.newtmpclient.entities.UserCoords;
import com.example.andrey.newtmpclient.entities.UserRole;

import java.util.List;

public class Response {
    private String response;
    private User user;
    private Task task;
    private List<Task> taskList;
    private List<User> userList;
    private List<Comment> comments;
    private List<Address> addresses;
    private List<ContactOnAddress>contacts;
    private List<UserCoords>userCoordsList;
    private UserRole userRole;
    private Token token;
    public static final String ADD_ACTION_ADMIN = "addActionAdmin";
    public static final String ADD_TASKS_TO_USER = "addTasksToUser";
    public static final String ADD_COMMENTS = "add_comments";
    public static final String UPDATE_USER_ROLE_SUCCESS = "update_user_role_success";
    public static final String INSERT_USER_ROLE_SUCCESS = "insert_user_role_success";
    public static final String ADD_TASK_SUCCESS = "add_task_success";
    public static final String ADD_COMMENT_SUCCESS = "add_comment_success";
    public static final String ADD_ADDRESSES_TO_USER = "add_addresses_to_user";
    public static final String SUCCESS_ADD_USER = "success_add_user";
    public static final String GET_AWAY_GUEST = "getAwayGuest";
    public static final String SUCCESS_UPDATE_TASK = "success_update_task";
    public static final String ADD_LATEST_USER_COORDS = "add_latest_user_coords";
    public static final String SUCCESS_REMOVE_USER = "success_remove_user";
    public static final String SUCCESS_REMOVE_TASK = "success_remove_task";
    public static final String SUCCESS_UPDATE_TASKS = "success_update_tasks";
    public static final String NOT_SUCCESS_AUTH = "not_success_auth";
    public static final String SUCCESS_FIRE_ADD = "success_fire_add";

    public List<ContactOnAddress> getContacts() {
        return contacts;
    }

    public List<UserCoords> getUserCoordsList() {
        return userCoordsList;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public Response(User user, List<Task>taskList, String response) {
        this.user = user;
        this.taskList = taskList;
        this.response = response;
    }

    public Response(List<User> users, List<Task>taskList, String response) {
        this.userList = users;
        this.taskList = taskList;
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public User getUser() {
        return user;
    }

    public Task getTask() {
        return task;
    }

    public List<User> getUserList() {
        return userList;
    }

    public Token getToken() {
        return token;
    }
}
