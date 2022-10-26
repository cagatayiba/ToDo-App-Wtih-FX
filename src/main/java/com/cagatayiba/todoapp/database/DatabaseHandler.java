package com.cagatayiba.todoapp.database;
import com.cagatayiba.todoapp.modal.Task;
import com.cagatayiba.todoapp.modal.User;
import com.mysql.cj.jdbc.result.ResultSetImpl;

import java.sql.*;
import java.util.Locale;
import java.util.Optional;

public class DatabaseHandler extends Config{
    private Connection dbConnection;
    private static DatabaseHandler dbHandlerInstance;

    private DatabaseHandler() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/"
                + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
    }

    public static DatabaseHandler getInstance() throws SQLException, ClassNotFoundException {
        if(dbHandlerInstance!=null) return dbHandlerInstance;
        dbHandlerInstance = new DatabaseHandler();
        return dbHandlerInstance;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }
    public void addUser(User user){
        String insertQuery = "INSERT INTO " + Const.USERS_TABLE + "(" +
                Const.USERS_FULLNAME +  "," + Const.USERS_USERNAME + "," +
                Const.USERS_PASSWORD + "," + Const.USERS_GENDER + "," +
                Const.USERS_LOCATION + ") VALUES(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insertQuery);

            preparedStatement.setString(1,user.getFullName());
            preparedStatement.setString(2,user.getUsername());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setString(4,user.getGender().toLowerCase());
            preparedStatement.setString(5,user.getLocation());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public ResultSet getUser(String username, String password){
        ResultSet resultSet = null;
        String searchQuery = "SELECT * FROM " + Const.USERS_TABLE +
                " WHERE " + Const.USERS_USERNAME + "=?" + " AND " +
                Const.USERS_PASSWORD + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(searchQuery);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;


    }

    public Optional<ResultSet> getAllTasksById(int userID)  {
        String searchQuery = "SELECT * FROM " + Const.TASKS_TABLE +
                " WHERE " + Const.TASKS_USERID  + " =?";


        Optional<ResultSet> tasks = Optional.empty();
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(searchQuery);
            preparedStatement.setInt(1,userID);
            var res = preparedStatement.executeQuery();
            tasks = Optional.of(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public int getNumberOfTasks(int userID){
        String searchQuery = "SELECT COUNT(*) FROM " + Const.TASKS_TABLE +
                " WHERE " + Const.TASKS_USERID  + "=?";

        int count = -1;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(searchQuery);
            preparedStatement.setInt(1,userID);
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            count = res.getInt(1);
            assert (!res.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int insertTask(Task task){
        String insertQuery = "INSERT INTO " + Const.TASKS_TABLE +
                "(" + Const.TASKS_USERID  + "," + Const.TASKS_DATECREATED +
                "," + Const.TASKS_DESCRIPTION + "," + Const.TASKS_TASK
                + ") VALUES(?,?,?,?)";
        Integer key = null;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1,task.getUserid());
            preparedStatement.setTimestamp(2,task.getDateCreated());
            preparedStatement.setString(3,task.getDescription());
            preparedStatement.setString(4,task.getTask());


            preparedStatement.execute();

            /*
                getting the key from the preparedStatement
            */
            ResultSet keys = preparedStatement.getGeneratedKeys();
            keys.next();
            key = keys.getInt(1);
            if ((keys.next())) throw new AssertionError();


            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert (key!=null);
        return key;

    }

    public void updateTask(Timestamp datecreated, String description, String task, int taskId) throws SQLException, ClassNotFoundException {

        String query = "UPDATE "  + Const.TASKS_TABLE + " SET " + Const.TASKS_DATECREATED + "=?, " +
                Const.TASKS_DESCRIPTION + "=?, " +  Const.TASKS_TASK + "=? WHERE " +
                Const.TASKS_TASKID +"=?";

        System.out.println(query);


        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setTimestamp(1, datecreated);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, task);
        preparedStatement.setInt(4, taskId);
        preparedStatement.close();

    }

    public void deleteTaskById(int id){
        String deleteQuery = "DELETE FROM " + Const.TASKS_TABLE +
                " WHERE " + Const.TASKS_TASKID + " =?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(deleteQuery);

            preparedStatement.setInt(1,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
