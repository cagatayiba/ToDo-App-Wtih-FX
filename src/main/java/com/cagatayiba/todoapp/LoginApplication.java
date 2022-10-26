package com.cagatayiba.todoapp;

import com.cagatayiba.todoapp.database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class LoginApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login.fxml"));
        // FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("add-item.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("listView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("ToDoApp");
        stage.setScene(scene);
        stage.show();



    }

    public static void main(String[] args) {
        launch();


    }
}