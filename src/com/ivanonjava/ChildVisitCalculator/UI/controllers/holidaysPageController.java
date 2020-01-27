package com.ivanonjava.ChildVisitCalculator.UI.controllers;

import com.ivanonjava.ChildVisitCalculator.Main;
import com.ivanonjava.ChildVisitCalculator.domains.CalendarController;
import com.ivanonjava.ChildVisitCalculator.domains.DatabaseController;
import com.ivanonjava.ChildVisitCalculator.helpers.Converter;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

public class holidaysPageController implements Initializable {

    public VBox box;

    public DatePicker date;
    public RadioButton rBut_everyYear;

    public RadioButton onlyYear;
    public RadioButton onlyMonth;
    private static boolean isYEAR;
    private static boolean isMONTH;
    public RadioButton allTime;

    public RadioButton period;
    public DatePicker beginOfPeriod;
    public DatePicker endOfPeriod;

    public RadioButton isAddPeriod;
    public DatePicker endAddPeriod;

    private Collection<ToolBar> c = new ArrayList<>();

    public void addHoliday() {
        if (date.getEditor().getText() != null && !date.getEditor().getText().trim().equals("")) {
            if (endAddPeriod.getEditor().getText() != null && !endAddPeriod.getEditor().getText().trim().equals("") && isAddPeriod.isSelected()) {
                if (DatabaseController.addHolidays(date.getEditor().getText(), endAddPeriod.getEditor().getText())) {
                    showHolidays();
                }
            } else if (DatabaseController.addHolidays(date.getEditor().getText(), rBut_everyYear.isSelected())) {
                showHolidays();
            }
        }

    }

    private Control myHolidays(String date) {
        ToolBar toolBar = new ToolBar();
        String[] temp = date.split("-");
        Text text = new Text(String.format("%s.%s.%s", temp[2], temp[1], temp[0]));
        toolBar.getItems().add(text);
        Button button_delete = new Button("Удалить");
        button_delete.setOnMouseClicked(event -> {
            DatabaseController.removeHolidays(text.getText());
            box.getChildren().remove(toolBar);
            c.remove(toolBar);
        });
        toolBar.getItems().add(new Separator());
        toolBar.getItems().add(button_delete);
        c.add(toolBar);
        return toolBar;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date.setConverter(Converter.getConverter());
        beginOfPeriod.setConverter(Converter.getConverter());
        endOfPeriod.setConverter(Converter.getConverter());
        allTime.setSelected(true);
        showHolidays();
    }

    private void showHolidays() {
        box.getChildren().removeAll(c);
        if (!period.isSelected()) {
            for (Date holiday : DatabaseController.getHolidays(isYEAR, isMONTH)) {
                box.getChildren().add(myHolidays(holiday.toString()));
            }
        }
        if (period.isSelected()) {
            for (Date holiday : DatabaseController.getHolidays(beginOfPeriod.getEditor().getText(), endOfPeriod.getEditor().getText())) {
                box.getChildren().add(myHolidays(holiday.toString()));
            }
        }

    }

    public void backToPatientPage() {
        Main.setDocumentPage();
    }

    public void setOnlyMonth() {
        onlyMonth.setSelected(true);
        endOfPeriod.setDisable(true);
        beginOfPeriod.setDisable(true);
        allTime.setSelected(false);
        onlyYear.setSelected(false);
        period.setSelected(false);
        isYEAR = false;
        isMONTH = true;
        CalendarController.getNow();
        showHolidays();
    }

    public void setOnlyYear() {
        onlyYear.setSelected(true);
        endOfPeriod.setDisable(true);
        beginOfPeriod.setDisable(true);
        allTime.setSelected(false);
        onlyMonth.setSelected(false);
        period.setSelected(false);
        isMONTH = false;
        isYEAR = true;
        showHolidays();
    }

    public void setAllTime() {
        allTime.setSelected(true);
        endOfPeriod.setDisable(true);
        beginOfPeriod.setDisable(true);
        onlyMonth.setSelected(false);
        onlyYear.setSelected(false);
        period.setSelected(false);
        isMONTH = false;
        isYEAR = false;
        showHolidays();
    }

    public void setPeriod() {
        allTime.setSelected(false);
        onlyYear.setSelected(false);
        onlyMonth.setSelected(false);
        period.setSelected(true);
        isYEAR = false;
        isMONTH = false;
        endOfPeriod.setDisable(false);
        beginOfPeriod.setDisable(false);
    }

    public void showPeriod() {

        if (beginOfPeriod.getEditor().getText() != null
                && endOfPeriod.getEditor().getText() != null
                && !endOfPeriod.getEditor().getText().equals("")
                && !beginOfPeriod.getEditor().getText().equals("")) {
            showHolidays();
        }
    }

    public void setAddPeriod() {
        endAddPeriod.setDisable(!isAddPeriod.isSelected());
        rBut_everyYear.setSelected(false);
    }
}
