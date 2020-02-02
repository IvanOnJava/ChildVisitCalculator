package com.ivanonjava.ChildVisitCalculator;

import com.ivanonjava.ChildVisitCalculator.helpers.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    private static Stage stage;

    private static Scene DocumentScene;
    private static Scene CalendarScene;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {
        initScene();
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(Constants.getInstance().TEMPLATES_URL + "main" + Constants.getInstance().POST_F));
        stage.setTitle(Constants.getInstance().TITLE);
        stage.setMinWidth(Constants.getInstance().MAIN_PAGE_MIN_WIDTH);
        stage.setMinHeight(Constants.getInstance().MAIN_PAGE_MIN_HEIGHT);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Constants.getInstance().PATH_ICON_MAIN));

        stage.setScene(new Scene(root));
        stage.show();

    }

    private void initScene() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Main.class.getResource(Constants.getInstance().TEMPLATES_URL + "document" + Constants.getInstance().POST_F));
            DocumentScene = new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            parent = FXMLLoader.load(Main.class.getResource(Constants.getInstance().TEMPLATES_URL + "newHolidays" + Constants.getInstance().POST_F));
            CalendarScene = new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void setDocumentPage() {

        stage.setResizable(true);

        stage.setMinWidth(Constants.getInstance().DOCUMENT_PAGE_MIN_WIDTH);
        stage.setMinHeight(Constants.getInstance().DOCUMENT_PAGE_MIN_HEIGHT);
        stage.setMaxHeight(Double.MAX_VALUE);
        stage.setMaxWidth(Double.MAX_VALUE);
        stage.setScene(DocumentScene);
        stage.setMaximized(true);
    }

    public static void setHolidaysPage() {
        stage.setResizable(false);
        stage.setScene(CalendarScene);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setMaxHeight(600);
        stage.setMinWidth(800);
        stage.setMaximized(false);

    }

    public static Stage getStage() {

        return stage;
    }
}
