package com.cagatayiba.todoapp.controller;

import com.cagatayiba.todoapp.database.DatabaseHandler;
import com.cagatayiba.todoapp.enums.Gender;
import com.cagatayiba.todoapp.modal.User;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Locale;

public class SignUpController {
    @FXML
    private CheckBox signUpCBFemale;

    @FXML
    private CheckBox signUpCBMale;

    @FXML
    private TextField signUpFullName;

    @FXML
    private TextField signUpLocation;

    @FXML
    private TextField signUpPassword;

    @FXML
    private JFXButton signUpSubmit;

    @FXML
    private TextField signUpUsername;

    @FXML
    void initialize(){

    }

    @FXML
    private void signUpUser(){
        User newUser = new User(signUpFullName.getText(), signUpUsername.getText(),
                signUpPassword.getText(), getGender(), signUpLocation.getText());
        try {
            DatabaseHandler.getInstance().addUser(newUser);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getGender(){
        Boolean isFemale = signUpCBFemale.isSelected();
        Boolean isMale = signUpCBMale.isSelected();
        int flag = -1;
        if(isMale && !isFemale) flag = 0;
        else if(isFemale && !isMale) flag = 1;
        return Gender.getByValue(flag).toString().toLowerCase(Locale.ROOT);


    }
}
