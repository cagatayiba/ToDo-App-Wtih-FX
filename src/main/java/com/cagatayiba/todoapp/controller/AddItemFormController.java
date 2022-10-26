package com.cagatayiba.todoapp.controller;

import com.cagatayiba.todoapp.auth.Credentials;
import com.cagatayiba.todoapp.database.DatabaseHandler;
import com.cagatayiba.todoapp.modal.Task;
import com.jfoenix.controls.JFXButton;
import com.mysql.cj.util.DnsSrv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

public class AddItemFormController {
    @FXML
    private TextField descriptionField;

    @FXML
    private Label infoLabel;

    @FXML
    private JFXButton saveButton;

    @FXML
    private Label successLabel;

    @FXML
    private TextField taskField;

    @FXML
    private JFXButton toDosButton;


    private int userId ;

    @FXML
    void initialize(){
        toDosButton.setOnAction(event1 -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/cagatayiba/todoapp/listView.fxml"));
            //fxmlLoader.setLocation(getClass().getResource("/com/cagatayiba/todoapp/add-item.fxml"));
            Pane root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("button is clicked");
            assert (root!=null);
            //((Node)event1.getSource()).getScene().getWindow().hide();
            /*
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            */
            //((Node)event1.getSource()).getScene().getWindow().hide();

            ((Stage)((Node)event1.getSource()).getScene().getWindow()).setScene(new Scene(root));

        });

        try {
            setToDosButtonText();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void saveTask(ActionEvent event) {
        Task newTask = new Task();

        //initializing params
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp ts = new java.sql.Timestamp(calendar.getTimeInMillis());
        String description = descriptionField.getText();
        String task = taskField.getText();

        // check params
        if(task.equals("") || description.equals("")){
            // show error message
            infoLabel.setText("please fill the fields");
            return;
        }

        // setting up the task's params
        newTask.setUserid(getUserId());
        newTask.setDateCreated(ts);
        newTask.setDescription(descriptionField.getText());
        newTask.setTask(taskField.getText());

        try {
            DatabaseHandler.getInstance().insertTask(newTask);

            //after successful insert set the label and button
            successLabel.setVisible(true);
            toDosButton.setVisible(true);
            setToDosButtonText();
            //after successful insert clear text field
            taskField.setText("");
            descriptionField.setText("");


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private void setToDosButtonText() throws SQLException, ClassNotFoundException {
        int taskNumber = DatabaseHandler.getInstance().getNumberOfTasks(Credentials.getUserId());
        toDosButton.setText("My 2Do's: " + taskNumber);
    }
}
