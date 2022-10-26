module com.cagatayiba.todoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;
    requires mysql.connector.java;
    requires junit;


    opens com.cagatayiba.todoapp to javafx.fxml;
    exports com.cagatayiba.todoapp;
    exports com.cagatayiba.todoapp.controller;
    opens com.cagatayiba.todoapp.controller to javafx.fxml;
    exports com.cagatayiba.todoapp.test;
    opens com.cagatayiba.todoapp.test to javafx.fxml;
}