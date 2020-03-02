package com.ivanonjava.ChildVisitCalculator.UI.controllers;

import com.ivanonjava.ChildVisitCalculator.Main;
import com.ivanonjava.ChildVisitCalculator.domains.CalendarController;
import com.ivanonjava.ChildVisitCalculator.domains.DatabaseController;
import com.ivanonjava.ChildVisitCalculator.domains.FileController;
import com.ivanonjava.ChildVisitCalculator.dynamicPages.*;
import com.ivanonjava.ChildVisitCalculator.helpers.Constants;
import com.ivanonjava.ChildVisitCalculator.helpers.HelperForDatepicker;
import com.ivanonjava.ChildVisitCalculator.helpers.Reasons;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class DocumentPageControllers implements Initializable {

    public javafx.scene.control.TabPane pane;
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

    private static Set<DocumentTable<?>> setTables = new HashSet<>();
    private static PatientPage page;
    private Set<Integer> addressSet;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageReload.setImage(new Image(Constants.getInstance().PATH_ICON_RELOAD));
        searchBeginDate.setConverter(HelperForDatepicker.getConverter());
        searchEndDate.setConverter(HelperForDatepicker.getConverter());

        searchBeginDate.setConverter(HelperForDatepicker.getConverter());
        searchEndDate.setConverter(HelperForDatepicker.getConverter());
        saveBeginDate.setConverter(HelperForDatepicker.getConverter());
        saveEndDate.setConverter(HelperForDatepicker.getConverter());
        imageReload.setOnMouseClicked(this::updateAllTable);

        addressSet = new HashSet<>();
        createAllTables();
    }

    private void createAllTables() {
        ArrayList<Integer> numbers = DatabaseController.getAllNumber();
        if (!Arrays.equals(addressSet.toArray(), numbers.toArray())) {
            addressSet.clear();
            addressSet.addAll(numbers);
            Set<DocumentTable<?>> set = new HashSet<>();
            Tab[] tab_temp = new Tab[numbers.size() + 1];
            Tab MagazineTab = new Tab(Constants.getInstance().TAB_MAGAZINE_NAME);
            MagazineTable mTable = new MagazineTable();
            MagazineTab.setContent(mTable);
            int j = 0;
            tab_temp[j++] = MagazineTab;
            set.add(mTable);
            for (int i : addressSet) {
                Tab t = new Tab(Constants.getInstance().TAB_PATRONAGE_NAME + i);
                PatronageTable table = new PatronageTable(i);
                t.setContent(table);
                tab_temp[j++] = t;
                set.add(table);
            }
            pane.getTabs().setAll(tab_temp);
            setTables.addAll(set);
        }
        update();
    }


    public void saveDiary() {
        if (getDate(saveBeginDate).trim().equalsIgnoreCase("") || getDate(saveEndDate).trim().equalsIgnoreCase(""))
            return;
        checkDateForDocuments();
        for (int i : DatabaseController.getAllNumber()) {
            FileController.writePatientsDiaryToFile(getDate(saveBeginDate), getDate(saveEndDate), i);
        }
    }

    public static void update() {
        setTables.forEach(DocumentTable::updateTable);
    }

    public void updateAllTable(MouseEvent event) {
        createAllTables();
    }

    public static void reopenAddPatientPage() {
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
            reopenAddPatientPage();
    }

    private void selectByAction(String action) {
        checkSearchDate();
        beginDate = getDate(searchBeginDate);
        endDate = getDate(searchEndDate);
        check = checkPeriod.isSelected();
        search = action;
        updateAllTable(null);
    }

    private void checkSearchDate() {
        if(CalendarController.getDate(getDate(searchEndDate)).before(CalendarController.getDate(getDate(searchBeginDate)))){
            String temp = getDate(searchBeginDate);
            searchBeginDate.getEditor().setText(getDate(searchEndDate));
            searchEndDate.getEditor().setText(temp);

        }

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
        checkDateForDocuments();
        for (DocumentTable<?> table : setTables) {
            table.saveDocument(getDate(saveBeginDate), getDate(saveEndDate));
        }
    }

    private void checkDateForDocuments() {
        if(CalendarController.getDate(getDate(saveEndDate)).before(CalendarController.getDate(getDate(saveBeginDate)))){
            String temp = getDate(saveBeginDate);
            saveBeginDate.getEditor().setText(getDate(saveEndDate));
            saveEndDate.getEditor().setText(temp);
        }
    }

    public void openReportStage() {
        if (getDate(saveBeginDate).trim().equalsIgnoreCase("") || getDate(saveEndDate).trim().equalsIgnoreCase(""))
            return;
        reportStage().show();
    }

    private Stage reportStage() {
        checkDateForDocuments();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.getIcons().add(new Image(Constants.getInstance().PATH_ICON_INFO));
        stage.setTitle("Отчет за [" + getDate(saveBeginDate) + " - " + getDate(saveEndDate) + "]");
        AnchorPane pane = new AnchorPane();
        TextArea textArea = new TextArea();
        textArea.setText(DatabaseController.getInfoAboutBJC(getDate(saveBeginDate), getDate(saveEndDate)));
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

    public static boolean checkSerial(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            for(Reasons r : Reasons.values())
                if(text.trim().equalsIgnoreCase(r.getReasons())) return true;
            return false;
        }
    }

}
