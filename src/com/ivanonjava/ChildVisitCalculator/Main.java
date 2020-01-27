package com.ivanonjava.ChildVisitCalculator;

import com.ivanonjava.ChildVisitCalculator.domains.CalendarController;
import com.ivanonjava.ChildVisitCalculator.domains.DatabaseController;
import com.ivanonjava.ChildVisitCalculator.domains.FileController;

import com.ivanonjava.ChildVisitCalculator.helpers.Converter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    private static Stage stage;

    private static final String TEMPLATES_URL = "UI/templates/";
    private static final String POST_F = "Page.fxml";

    public static void main(String[] args) {
        CalendarController.Instantiate();
        DatabaseController.Instantiate();
        FileController.Instantiate();
        Converter.getInstance();
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {

        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(String.format("%s%s%s", TEMPLATES_URL, "main", POST_F)));
        stage.setTitle(Constants.TITLE);
        stage.setMinWidth(Constants.MAIN_PAGE_MIN_WIDTH);
        stage.setMinHeight(Constants.MAIN_PAGE_MIN_HEIGHT);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Constants.PATH_ICON_MAIN));
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void setDocumentPage() {

        stage.setResizable(true);
        stage.setMinWidth(Constants.DOCUMENT_PAGE_MIN_WIDTH);
        stage.setMinHeight(Constants.DOCUMENT_PAGE_MIN_HEIGHT);
        setPage("document");
    }

    public static void setHolidaysPage() {

        setPage("holidays");
    }

    private static void setPage(String page) {


        String str = String.format("%s%s%s", TEMPLATES_URL, page, POST_F);
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(str));
            stage.setScene(new Scene(root));
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getStage() {

        return stage;
    }
}
