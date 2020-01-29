package com.ivanonjava.ChildVisitCalculator.UI.controllers;

import com.ivanonjava.ChildVisitCalculator.Main;
import com.ivanonjava.ChildVisitCalculator.domains.CalendarController;
import com.ivanonjava.ChildVisitCalculator.helpers.CalendarButton;
import com.ivanonjava.ChildVisitCalculator.pojo.Week;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewHolidaysPageControllers implements Initializable {

    public Text cellYEAR;
    public GridPane gJan;
    public GridPane gFeb;
    public GridPane gMar;
    public GridPane gApr;
    public GridPane gMay;
    public GridPane gJun;
    public GridPane gJul;
    public GridPane gAvg;
    public GridPane gSep;
    public GridPane gOct;
    public GridPane gNov;
    public GridPane gDec;


    public void reduceYear() {
        cellYEAR.setText(String.valueOf(Integer.parseInt(cellYEAR.getText()) - 1));
        config();
    }

    public void increaseYear() {
        cellYEAR.setText(String.valueOf(Integer.parseInt(cellYEAR.getText()) + 1));
        config();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cellYEAR.setText(new SimpleDateFormat("YYYY").format(CalendarController.getNow()));
        config();
    }

    private void config() {
        update(gJan, gFeb, gMar, gApr, gMay, gJun, gJul, gAvg, gSep, gOct, gNov, gDec);
    }

    private void update(GridPane... panes) {
        int month = 0;
        for (GridPane pane : panes) {
            pane.getChildren().clear();
            pane.setGridLinesVisible(true);

            ArrayList<Week> weeks = CalendarController.getDaysForMonth(month++, Integer.parseInt(cellYEAR.getText()));
            for (int i = 0; i < weeks.size(); i++) {
                CalendarButton cMonday = new CalendarButton(weeks.get(i).getMonday(), String.valueOf(month), cellYEAR.getText());
                CalendarButton cTuesday = new CalendarButton(weeks.get(i).getTuesday(), String.valueOf(month), cellYEAR.getText());
                CalendarButton cWednesday = new CalendarButton(weeks.get(i).getWednesday(), String.valueOf(month), cellYEAR.getText());
                CalendarButton cThursday = new CalendarButton(weeks.get(i).getThursday(), String.valueOf(month), cellYEAR.getText());
                CalendarButton cFriday = new CalendarButton(weeks.get(i).getFriday(), String.valueOf(month), cellYEAR.getText());
                CalendarButton cSaturday = new CalendarButton(weeks.get(i).getSaturday(), String.valueOf(month), cellYEAR.getText());
                CalendarButton cSunday = new CalendarButton(weeks.get(i).getSunday(), String.valueOf(month), cellYEAR.getText());

                pane.add(cMonday, 0, i);
                pane.add(cTuesday, 1, i);
                pane.add(cWednesday, 2, i);
                pane.add(cThursday, 3, i);
                pane.add(cFriday, 4, i);
                pane.add(cSaturday, 5, i);
                pane.add(cSunday, 6, i);
            }
        }

    }

    public void setDocumentPage() {
        Main.setDocumentPage();
    }
}
