package com.cagatayiba.todoapp.controller;

import com.cagatayiba.todoapp.animation.Shaker;
import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class AddItemController {

    @FXML
    private AnchorPane addItemRootPane;

    @FXML
    private ImageView addButton;

    @FXML
    private Label noTaskLabel;

    private int userId;

    @FXML
    void initialize(){
        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            Shaker buttonShaker = new Shaker(addButton);
            buttonShaker.shake();
            loadAddItemForm();
        });
    }

    private void loadAddItemForm()  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/cagatayiba/todoapp/addItemForm.fxml"));
        AnchorPane addItemFormPane = null;

        try {
            addItemFormPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert (addItemFormPane!=null);
        AddItemFormController addItemFormController = loader.getController();
        assert (addItemFormController!=null);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), addItemFormPane);
        fadeTransition.setFromValue(0f);
        fadeTransition.setToValue(1f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        fadeTransition.play();

        addItemFormController.setUserId(getUserId());
        addItemRootPane.getChildren().setAll(addItemFormPane);
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getUserId(){
        return userId;
    }


}
