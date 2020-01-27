package com.ivanonjava.ChildVisitCalculator.UI.controllers;


import com.ivanonjava.ChildVisitCalculator.Constants;
import com.ivanonjava.ChildVisitCalculator.Main;
import com.ivanonjava.ChildVisitCalculator.domains.DatabaseController;
import com.ivanonjava.ChildVisitCalculator.helpers.Info;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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

    public void openVersionInfo() {
        TextArea text = new TextArea(Info.getInfo() + "\n" + Info.getVersionInfo());
        text.setEditable(false);
        StackPane pane = new StackPane();
        pane.getChildren().add(text);
        Scene infoScene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle("Version info");
        stage.setScene(infoScene);
        stage.getIcons().add(new Image(Constants.PATH_ICON_INFO));
        stage.show();
    }


}
