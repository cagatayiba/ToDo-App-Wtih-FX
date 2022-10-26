package com.cagatayiba.todoapp.database;

public class Const {
    public static final String USERS_TABLE = "users";
    public static final String TASKS_TABLE = "tasks";

    //Column names for users
    public static final String USERS_USERID = "userid";
    public static final String USERS_FULLNAME = "fullname";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_GENDER = "gender";
    public static final String USERS_LOCATION = "location";

    //Column names for tasks
    public static final String TASKS_TASKID = "taskid";
    public static final String TASKS_USERID = "userid";
    public static final String TASKS_DATECREATED = "datecreated";
    public static final String TASKS_DESCRIPTION = "description";
    public static final String TASKS_TASK = "task";
}
