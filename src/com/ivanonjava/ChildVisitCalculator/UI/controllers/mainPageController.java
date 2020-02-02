package com.ivanonjava.ChildVisitCalculator.UI.controllers;


import com.ivanonjava.ChildVisitCalculator.Main;
import com.ivanonjava.ChildVisitCalculator.domains.DatabaseController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class mainPageController implements Initializable {
    public TextField login;
    public PasswordField pass;
    public Label infoLabel;

    public void enter() {

        if (DatabaseController.validateUser(0, login.getText(), pass.getText())) {
            Main.setDocumentPage();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }




}
