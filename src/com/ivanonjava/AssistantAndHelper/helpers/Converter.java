package com.ivanonjava.AssistantAndHelper.helpers;

import com.ivanonjava.AssistantAndHelper.Constants;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Converter {

    private static Converter instance;
    private static StringConverter<LocalDate> converter;
    private Converter(){
        converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(Constants.DATE_PATTERN);

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

    public static Converter getInstance() {
        if(instance == null){
            instance = new Converter();
        }
        return instance;
    }

    public static StringConverter<LocalDate> getConverter() {
        return converter;
    }
}
