package com.cagatayiba.todoapp.controller;

import com.cagatayiba.todoapp.animation.Shaker;
import com.cagatayiba.todoapp.auth.Credentials;
import com.cagatayiba.todoapp.database.Const;
import com.cagatayiba.todoapp.database.DatabaseHandler;
import com.cagatayiba.todoapp.modal.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.jfoenix.controls.JFXButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;

public class LoginController {
    @FXML
    private TextField loginPassword;

    @FXML
    private JFXButton loginSignupButton;

    @FXML
    private JFXButton loginSubmitButton;

    @FXML
    private TextField loginUsername;

    @FXML
    private Label infoLabel;



    @FXML
    void initialize(){

    }

    @FXML
    private void loginUser(ActionEvent event){
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        if(username.equals("") || password.equals("")){
            shakeCredentials();
            infoLabel.setText("please fill the fields");
            return;
        }
        //check in the database if the user exists

        try {
            ResultSet resultSet = DatabaseHandler.getInstance().getUser(username,password);
            if(resultSet.next()){
                ((JFXButton)event.getSource()).getScene().getWindow().hide();
                int userID = resultSet.getInt(Const.USERS_USERID);
                String usrnm = resultSet.getString(Const.USERS_USERNAME);
                Credentials.setUserId(userID);
                Credentials.setUsername(usrnm);
                showAddItemPage(userID);
                assertFalse(resultSet.next());
            }else {
                shakeCredentials();
                infoLabel.setText("wrong credentials");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void signupUser(ActionEvent event){
        ((JFXButton)event.getSource()).getScene().getWindow().hide();
        showPage("signUp" , Optional.ofNullable(null));
    }


    private void showAddItemPage(int userId){
        showPage("add-item", Optional.ofNullable(userId));
    }
    private void showPage(String page, Optional<Integer> userId){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/cagatayiba/todoapp/" + page + ".fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if(userId.isPresent()){
            AddItemController addItemController = loader.getController();
            addItemController.setUserId(userId.get());
        }
        stage.showAndWait();
    }

    private void shakeCredentials(){
        Shaker usernameShaker = new Shaker(loginUsername);
        Shaker passwordShaker = new Shaker(loginPassword);
        usernameShaker.shake();
        passwordShaker.shake();
    }
}