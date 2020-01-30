package com.ivanonjava.ChildVisitCalculator.UI.controllers;

import com.ivanonjava.ChildVisitCalculator.Constants;
import com.ivanonjava.ChildVisitCalculator.Main;
import com.ivanonjava.ChildVisitCalculator.Pages;
import com.ivanonjava.ChildVisitCalculator.domains.DatabaseController;
import com.ivanonjava.ChildVisitCalculator.domains.FileController;
import com.ivanonjava.ChildVisitCalculator.dynamicPages.*;
import com.ivanonjava.ChildVisitCalculator.helpers.Converter;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DocumentPageControllers implements Initializable {

    public javafx.scene.control.TabPane TabPane;
    public ImageView imageReload;

    public DatePicker searchBeginDate;
    public static String beginDate = "";
    public DatePicker searchEndDate;
    public static String endDate = "";
    public CheckBox checkPeriod;
    public static boolean check = false;
    public static String search = "null";
    public DatePicker saveBeginDate;
    public DatePicker saveEndDate;
    private Tooltip tooltip = new Tooltip("Обновить таблицы");

    private static List<DocumentTable<?>> listTables = new ArrayList<>();
    private static PatientPage page;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageReload.setImage(new Image(Constants.PATH_ICON_RELOAD));
        searchBeginDate.setConverter(Converter.getConverter());
        searchEndDate.setConverter(Converter.getConverter());

        searchBeginDate.setConverter(Converter.getConverter());
        searchEndDate.setConverter(Converter.getConverter());
        saveBeginDate.setConverter(Converter.getConverter());
        saveEndDate.setConverter(Converter.getConverter());
        createAllTables();
    }

    private void createAllTables() {
        TabPane.getTabs().clear();
        listTables.clear();
        Tab MagazineTab = new Tab(Constants.TAB_MAGAZINE_NAME);
        MagazineTable mTable = new MagazineTable();
        MagazineTab.setContent(mTable);
        TabPane.getTabs().add(MagazineTab);
        listTables.add(mTable);
        for (int i : DatabaseController.getAllNumber()) {
            Tab tab = new Tab(Constants.TAB_PATRONAGE_NAME + i);
            PatronageTable table = new PatronageTable(i);
            tab.setContent(table);
            TabPane.getTabs().add(tab);
            listTables.add(table);
        }
        update();
    }

    public void saveDiary() {
        if (getDate(saveBeginDate).trim().equalsIgnoreCase("") || getDate(saveEndDate).trim().equalsIgnoreCase(""))
            return;
        for (int i : DatabaseController.getAllNumber()) {
            FileController.writePatientsDiaryToFile(getDate(saveBeginDate), getDate(saveEndDate), i);
        }
    }

    public static void update() {
        listTables.forEach(DocumentTable::updateTable);
    }

    public void updateAllTable() {
        createAllTables();
    }

    public static void reopen() {
        page.close();
        openAddPatientPage();
    }

    private static void openAddPatientPage() {
        page = new PatientPage();
        page.initModality(Modality.NONE);
        page.initOwner(Main.getStage());
        page.show();
    }

    public void openAddPatient() {
        if (page == null)
            openAddPatientPage();
        else
            reopen();
    }

    private void selectByAction(String action) {
        beginDate = getDate(searchBeginDate);
        endDate = getDate(searchEndDate);
        check = checkPeriod.isSelected();
        search = action;
        updateAllTable();
    }

    public void searchForBirthday() {
        selectByAction("birthday");
    }

    public void searchForDiscard() {
        selectByAction("discardday");
    }

    public void searchForWent() {
        selectByAction("");
    }

    public void clearSearch() {
        searchEndDate.getEditor().clear();
        searchBeginDate.getEditor().clear();
        checkPeriod.setSelected(false);
        searchEndDate.setDisable(true);
        selectByAction("null");
    }

    public void selectPeriod() {
        searchEndDate.setDisable(!checkPeriod.isSelected());
        if (!checkPeriod.isSelected()) {
            searchEndDate.getEditor().clear();
        }
    }

    private String getDate(DatePicker date) {
        return date.getEditor().getText().trim();
    }

    public void editAddress() {
        AddressPage page = new AddressPage();
        page.initModality(Modality.NONE);
        page.initOwner(Main.getStage());
        page.show();
    }

    public void saveDocuments() {
        if (getDate(saveBeginDate).trim().equalsIgnoreCase("") || getDate(saveEndDate).trim().equalsIgnoreCase(""))
            return;
        for (DocumentTable<?> table : listTables) {
            table.saveDocument(getDate(saveBeginDate), getDate(saveEndDate));
        }
    }

    public void openReportStage() {
        if (getDate(saveBeginDate).trim().equalsIgnoreCase("") || getDate(saveEndDate).trim().equalsIgnoreCase(""))
            return;
        reportStage().show();
    }

    private Stage reportStage() {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.getIcons().add(new Image(Constants.PATH_ICON_INFO));
        stage.setTitle("Отчет за [" + getDate(saveBeginDate) + " - " + getDate(saveEndDate) + "]");
        AnchorPane pane = new AnchorPane();
        TextArea textArea = new TextArea();
        textArea.setText(DatabaseController.getTextForTutu(getDate(saveBeginDate), getDate(saveEndDate)));
        AnchorPane.setRightAnchor(textArea, 5.0);
        AnchorPane.setLeftAnchor(textArea, 5.0);
        AnchorPane.setTopAnchor(textArea, 5.0);
        AnchorPane.setBottomAnchor(textArea, 5.0);
        pane.getChildren().add(textArea);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        return stage;
    }

    public void getUpdateTooltip() {
        Point2D p = imageReload.localToScreen(imageReload.getLayoutBounds().getMaxX(), imageReload.getLayoutBounds().getMaxY());
        tooltip.show(imageReload, p.getX(), p.getY());
    }

    public void hideUpdateTooltip() {
        tooltip.hide();
    }

    public void openCalendar() {
        Main.setHolidaysPage();
    }
}
