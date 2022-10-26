package com.cagatayiba.todoapp.modal;

import java.sql.Timestamp;

public class Task {
    private int taskId;
    private int userid;
    private Timestamp dateCreated;
    private String description;
    private String task;

    public Task() {
    }

    public Task(int userid, Timestamp dateCreated, String description, String task) {
        this.userid = userid;
        this.dateCreated = dateCreated;
        this.description = description;
        this.task = task;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
