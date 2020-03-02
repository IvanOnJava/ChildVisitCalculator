package com.ivanonjava.ChildVisitCalculator.helpers;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class HelperForDatepicker {

    private static StringConverter<LocalDate> converter;

    private static TextFormatter<Date> formatter;
    static {
        converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(Constants.getInstance().DATE_PATTERN);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };


    }

    public static TextFormatter<Date> getFormatter(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        DateStringConverter converter = new DateStringConverter(dateFormat);
        return   new TextFormatter<>(converter, null, c -> {
            if (c.isContentChange()) {
                if (c.getControlNewText().length() == 10) {
                    try {
                        dateFormat.parse(c.getControlNewText());
                    } catch (ParseException ex) {
                        c.getControl().setStyle("-fx-background-color: red;");
                    }
                }
                else{
                    c.getControl().setStyle(null);
                }
            }
            if (c.isAdded()) {
                if (c.getControlNewText().length() > 10)
                {
                    return null;
                }
                int caretPosition = c.getCaretPosition();
                if (caretPosition == 2 || caretPosition == 5) {
                    c.setText(c.getText() + ".");
                    c.setCaretPosition(c.getControlNewText().length());
                    c.setAnchor(c.getControlNewText().length());
                }
            }
            return c;
        });
    }
    public static StringConverter<LocalDate> getConverter() {
        return converter;
    }

}
