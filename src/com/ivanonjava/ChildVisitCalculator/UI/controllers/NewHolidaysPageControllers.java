package com.ivanonjava.ChildVisitCalculator.UI.controllers;

import com.ivanonjava.ChildVisitCalculator.pojo.Week;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class NewHolidaysPageControllers implements Initializable {

    public Text cellYEAR;
    public TableView<Week> tableJan;
    public TableView<Week> tableFeb;
    public TableView<Week> tableMar;
    public TableView<Week> tableApr;
    public TableView<Week> tableMay;
    public TableView<Week> tableJun;
    public TableView<Week> tableJul;
    public TableView<Week> tableAvg;
    public TableView<Week> tableSep;
    public TableView<Week> tableOct;
    public TableView<Week> tableNov;
    public TableView<Week> tableDec;


    public void reduceYear(ActionEvent actionEvent) {
        cellYEAR.setText(String.valueOf(Integer.parseInt(cellYEAR.getText()) - 1));
    }

    public void increaseYear(ActionEvent actionEvent) {
        cellYEAR.setText(String.valueOf(Integer.parseInt(cellYEAR.getText()) + 1));
    }
    ObservableList<Week> weeks = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        config(tableJan, tableFeb, tableMar, tableApr, tableMay, tableJun, tableJul, tableAvg, tableSep, tableOct, tableNov, tableDec);

    }

    @SafeVarargs
    private final void config(TableView<Week>... tableMonth) {
        for(TableView<Week> table : tableMonth){
            table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("monday"));
            table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("tuesday"));
            table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("wednesday"));
            table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("thursday"));
            table.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("friday"));
            table.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("saturday"));
            table.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("sunday"));
        }
    }
}
