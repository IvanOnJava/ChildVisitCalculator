package com.ivanonjava.ChildVisitCalculator.helpers;

import com.ivanonjava.ChildVisitCalculator.domains.DatabaseController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class CalendarButton extends Button {
    private String data;
    private boolean isHolidays;

    public CalendarButton(String day, String months, String year) {
        super(day);
        if (day.equals("")) {
            this.setDisable(true);
            this.setVisible(false);
        } else {

            this.setOnAction(this::click);
            this.setFont(Font.font(10.0));
            this.setPrefSize(25.0, 25.0);
            this.setMaxSize(25.0, 25.0);
            this.setMinSize(25.0, 25.0);
            data = day.trim() + "." + months.trim() + "." + year.trim();
            if (DatabaseController.isWeekend(data)) {
                this.setStyle("-fx-background-color: red;");
                isHolidays = true;
            }
            else {
                this.setStyle("-fx-background-color: inherit");
                isHolidays = false;
            }

        }

    }

    private void click(ActionEvent actionEvent) {

        if (!isHolidays) {
            if (DatabaseController.addHolidays(data, false)) {
                this.setStyle("-fx-background-color: red");
                isHolidays = true;
            }
        } else if (DatabaseController.removeHolidays(data)) {
            this.setStyle("-fx-background-color: inherit");
            isHolidays = false;
        }
    }


}
