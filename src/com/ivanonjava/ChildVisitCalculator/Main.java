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



    public static void main(String[] args) {

        CalendarController.Instantiate();
        DatabaseController.Instantiate();
        FileController.Instantiate();
        Converter.getInstance();
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {

        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(Pages.getPage(Pages.MAIN)));
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
        stage.setMaxHeight(Double.MAX_VALUE);
        stage.setMaxWidth(Double.MAX_VALUE);
        setPage(Pages.DOCUMENT);
        stage.setMaximized(true);
    }

    public static void setHolidaysPage() {
        stage.setResizable(false);

        setPage(Pages.newHOLIDAYS);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setMaxHeight(600);
        stage.setMinWidth(800);
        stage.setMaximized(false);

    }

    private static void setPage(Pages page) {


        String str = Pages.getPage(page);
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
