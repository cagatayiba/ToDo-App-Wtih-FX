package com.cagatayiba.todoapp.auth;

public class Credentials {
    private static String username;
    private static int userId;
    public static String getUsername(){
        return username;
    }
    public static void setUsername(String username){
        Credentials.username = username;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Credentials.userId = userId;
    }
}
