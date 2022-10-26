package com.cagatayiba.todoapp.test;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CustomListCellController {
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

}
