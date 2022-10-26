package com.cagatayiba.todoapp.controller;

import com.cagatayiba.todoapp.modal.Task;
import com.cagatayiba.todoapp.test.CustomListCellController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateTaskController {
    @FXML
    private TextField updateDescriptionField;

    @FXML
    private Label updateInfoLabel;

    @FXML
    private JFXButton updateSaveButton;

    @FXML
    private Label updateSuccessLabel;

    @FXML
    private TextField updateTaskField;

    private ListViewController.CustomListCellController rootCellController;



    @FXML
    void updateTask(ActionEvent event) {
        assert (rootCellController!=null);
        String task = updateTaskField.getText();
        String description = updateDescriptionField.getText();
        if(task.equals("") || description.equals("")){
            updateInfoLabel.setText("please fill the fields");
            return;
        }
        rootCellController.updateTask(task, description);
        updateSuccessLabel.setText("task updated successfully");
        Node target  = (Node)event.getTarget();
        target.getScene().getWindow().hide();
    }


    public void setRootCellController(ListViewController.CustomListCellController rootCellController) {
        this.rootCellController = rootCellController;
    }
}
