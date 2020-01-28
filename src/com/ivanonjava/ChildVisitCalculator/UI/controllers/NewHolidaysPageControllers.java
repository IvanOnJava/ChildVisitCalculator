package com.ivanonjava.ChildVisitCalculator.UI.controllers;

import com.ivanonjava.ChildVisitCalculator.domains.CalendarController;
import com.ivanonjava.ChildVisitCalculator.pojo.Week;
import com.sun.javafx.css.Style;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.fxml.Initializable;
import javafx.scene.control.Cell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
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
        config();
    }
    private void config(){
        update(gJan, gFeb, gMar, gApr, gMay, gJun, gJul, gAvg, gSep, gOct, gNov, gDec);
    }
    private void update(GridPane... panes) {
        int month = 0;
        for(GridPane pane : panes){
                pane.getChildren().clear();
                pane.setGridLinesVisible(true);

            ArrayList<Week> weeks = CalendarController.getDaysForMonth(month++, Integer.parseInt(cellYEAR.getText()));
            for (int i = 0; i < weeks.size(); i++) {
                pane.add(new Text(weeks.get(i).getMonday()), 0, i);
                pane.add(new Text(weeks.get(i).getTuesday()), 1, i);
                pane.add(new Text(weeks.get(i).getWednesday()), 2, i);
                pane.add(new Text(weeks.get(i).getThursday()), 3, i);
                pane.add(new Text(weeks.get(i).getFriday()), 4, i);
                pane.add(new Text(weeks.get(i).getSaturday()), 5, i);
                pane.add(new Text(weeks.get(i).getSunday()), 6, i);
            }
            pane.getChildren().forEach(node -> {
                Text text = (Text) node;
                if(text.getText().trim().equals("3"))
                   ((Text) node).setStyle("-fx-text-fill: #f74948");
            });
        }

    }

}
