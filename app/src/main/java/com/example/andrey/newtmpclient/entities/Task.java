package com.example.andrey.newtmpclient.entities;

import java.util.List;

public class Task {
    private int id;
    private String created;
    private String importance;
    private String body;
    private String status;
    private String type;
    private String doneTime;
    private int userId;
    private int addressId;
    private String address;
    private String orgName;
    private List<Comment> comments;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(String doneTime) {
        this.doneTime = doneTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Task() {}

    @Override
    public String toString() {
        return "[id:" + id +", Важность:" + importance +", Статус:" + status + ", Тип:" + type
                + ", Выполнить до:" + doneTime + ", Что сделать:" + body +", Создана:" + created  + ", Юзер:" + userId +
                ", Адрес:"	+ addressId + "]";
    }

    public static class Builder{
        private int id;
        private String created;
        private String importance;
        private String body;
        private String status;
        private String type;
        private String doneTime;
        private int userId;
        private int addressId;
        private String address;
        private String orgName;
        private List<Comment> comments;

        public Builder id(int val){
            id = val;
            return this;
        }

        public Builder created(String val){
            created = val;
            return this;
        }

        public Builder importance(String val){
            importance = val;
            return this;
        }
        public Builder body(String val){
            body = val;
            return this;
        }
        public Builder status(String val){
            status = val;
            return this;
        }
        public Builder doneTime(String val){
            doneTime = val;
            return this;
        }
        public Builder type(String val){
            type = val;
            return this;
        }
        public Builder address(String val){
            address = val;
            return this;
        }
        public Builder orgName(String val){
            orgName = val;
            return this;
        }
        public Builder addressId(int val){
            addressId = val;
            return this;
        }
        public Builder userId(int val){
            userId = val;
            return this;
        }

        public Builder comments(List<Comment> val){
            comments = val;
            return this;
        }

        public Task build(){
            return new Task(this);
        }
    }

    private Task(Builder builder){
        this.id = builder.id;
        this.created = builder.created;
        this.importance = builder.importance;
        this.body = builder.body;
        this.status = builder.status;
        this.doneTime = builder.doneTime;
        this.userId = builder.userId;
        this.addressId = builder.addressId;
        this.address = builder.address;
        this.orgName = builder.orgName;
        this.type = builder.type;
        this.comments = builder.comments;
    }
}