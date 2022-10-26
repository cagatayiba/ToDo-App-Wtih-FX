package com.cagatayiba.todoapp.controller;

import com.cagatayiba.todoapp.auth.Credentials;
import com.cagatayiba.todoapp.database.Const;
import com.cagatayiba.todoapp.database.DatabaseHandler;
import com.cagatayiba.todoapp.modal.Task;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.CacheRequest;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListViewController {

    @FXML
    private Label successLabel;

    @FXML
    private Label infoLabel;

    @FXML
    private TextField listsDescriptionField;

    @FXML
    private JFXButton listsSaveButton;

    @FXML
    private TextField listsTaskField;

    @FXML
    private JFXListView<Task> tasksList;

    private ObservableList<Task> tasks;




    @FXML
    void initialize(){
        tasks = FXCollections.observableArrayList();
        try {
            Optional<ResultSet> res = DatabaseHandler.getInstance().getAllTasksById(Credentials.getUserId());
            //Optional<ResultSet> res = DatabaseHandler.getInstance().getAllTasksById(2);
            if(res.isEmpty()){
                System.out.println("an error occurred getting items from database");
                return;
            }
            ResultSet tasksCaptured  = res.get();
            while (tasksCaptured.next()){
                Task task = createTask(tasksCaptured);
                tasks.add(task);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        tasksList.setItems(tasks);
        tasksList.setCellFactory(CustomListCell -> new CustomListCell());
    }

    @FXML
    public void saveTask(){
        Task newTask = new Task();

        //initializing params
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp ts = new java.sql.Timestamp(calendar.getTimeInMillis());
        String description = listsDescriptionField.getText();
        String task = listsTaskField.getText();

        // check params
        if(task.equals("") || description.equals("")){
            // show error message
            infoLabel.setText("please fill the fields");
            return;
        }

        // setting up the task's params
        newTask.setUserid(Credentials.getUserId());
        newTask.setDateCreated(ts);
        newTask.setDescription(description);
        newTask.setTask(task);

        try {
            int key = DatabaseHandler.getInstance().insertTask(newTask);
            successLabel.setText("task added successfully");
            newTask.setTaskId(key);
            tasks.add(newTask);
            updateList();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            infoLabel.setText("something goes wrong try later");
        }


    }

    private static class CustomListCell extends JFXListCell<Task>{
        @Override
        public void updateItem(Task item, boolean empty){
            super.updateItem(item, empty);
            if(empty || item==null){
                setText(null);
                setGraphic(null);
                return;
            }


            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/cagatayiba/todoapp/customListCell.fxml"));
            fxmlLoader.setController(new CustomListCellController());
            fxmlLoader.setRoot(new AnchorPane());
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            CustomListCellController customListCellController = fxmlLoader.getController();
            assert (customListCellController!=null);
            customListCellController.setCellTaskLabelText(item.getTask());
            customListCellController.setCellDescriptionLabelText(item.getDescription());
            customListCellController.setCellTaskDate(item.getDateCreated().toString());
            customListCellController.setTaskId(item.getTaskId());


            //setting up the event handlers
            customListCellController.setMouseClickEventToDeleteIcon(mouseEvent -> {
                try {
                    DatabaseHandler.getInstance().deleteTaskById(getItem().getTaskId());
                    getListView().getItems().remove(getItem());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            customListCellController.setMouseClickEventToRefreshIcon(mouseEvent -> {
                FXMLLoader fxmlLoaderForUpdate = new FXMLLoader();
                fxmlLoaderForUpdate.setLocation(getClass().getResource("/com/cagatayiba/todoapp/updateTask.fxml"));

                Pane rootPane = null;
                try {
                    rootPane = fxmlLoaderForUpdate.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateTaskController updateTaskController = fxmlLoaderForUpdate.getController();
                updateTaskController.setRootCellController(customListCellController);
                Stage stage = new Stage();
                assert (rootPane!=null);
                stage.setScene(new Scene(rootPane));
                stage.show();
            });

            setText(null);
            setGraphic(customListCellController.getCellRootPane());

        }

    }
    protected static class CustomListCellController{
        @FXML
        private ImageView updateIcon;

        @FXML
        private ImageView assignmentImage;

        @FXML
        private ImageView cellDeleteTaskIcon;

        @FXML
        private Label cellDescriptionLabel;

        @FXML
        private AnchorPane cellRootPane;

        @FXML
        private Label cellTaskDate;

        @FXML
        private Label cellTaskLabel;

        private int taskId;

        @FXML
        void initialize(){

        }


        public void setCellTaskLabelText(String taskGiven){
            cellTaskLabel.setText(taskGiven);
        }
        public void setCellDescriptionLabelText(String description){
            cellDescriptionLabel.setText(description);
        }
        public void setCellTaskDate(String date){
            cellTaskDate.setText(date);
        }
        public AnchorPane getCellRootPane(){return cellRootPane;};
        protected void setMouseClickEventToDeleteIcon(EventHandler<MouseEvent> handler){
            cellDeleteTaskIcon.setOnMouseClicked(handler);
        }

        protected void setMouseClickEventToRefreshIcon(EventHandler<MouseEvent> handler){
            updateIcon.setOnMouseClicked(handler);
        }

        public void updateTask(String task, String description){
            cellTaskLabel.setText(task);
            cellDescriptionLabel.setText(description);
            try {
                Calendar calendar = Calendar.getInstance();
                java.sql.Timestamp ts = new java.sql.Timestamp(calendar.getTimeInMillis());
                assert (getTaskId()>0);
                System.out.println(getTaskId());
                DatabaseHandler.getInstance().updateTask(ts, description, task, getTaskId());
            } catch (SQLException | ClassNotFoundException e) {

                e.printStackTrace();
            }
        }

        private int getTaskId() {
            return taskId;
        }

        protected void setTaskId(int taskId) {
            this.taskId = taskId;
        }
    }


    private Task createTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();
        task.setTask(resultSet.getString(Const.TASKS_TASK));
        task.setDateCreated(resultSet.getTimestamp(Const.TASKS_DATECREATED));
        task.setUserid(resultSet.getInt(Const.TASKS_USERID));
        task.setDescription(resultSet.getString(Const.TASKS_DESCRIPTION));
        task.setTaskId(resultSet.getInt(Const.TASKS_TASKID));
        return task;
    }



    private void updateList(){

    }




}
