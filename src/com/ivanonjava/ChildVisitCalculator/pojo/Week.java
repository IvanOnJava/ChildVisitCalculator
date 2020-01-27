package com.ivanonjava.ChildVisitCalculator.pojo;

import javafx.beans.property.SimpleStringProperty;

public class Week {
    SimpleStringProperty monday;
    SimpleStringProperty tuesday;
    SimpleStringProperty wednesday;
    SimpleStringProperty thursday;
    SimpleStringProperty friday;
    SimpleStringProperty saturday;
    SimpleStringProperty sunday;

    public Week(String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday) {
        this.monday = new SimpleStringProperty(monday);
        this.tuesday = new SimpleStringProperty(tuesday);
        this.wednesday = new SimpleStringProperty(wednesday);
        this.thursday = new SimpleStringProperty(thursday);
        this.friday = new SimpleStringProperty(friday);
        this.saturday = new SimpleStringProperty(saturday);
        this.sunday = new SimpleStringProperty(sunday);
    }

    @Override
    public String toString() {
        return "Week{" +
                "monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                '}';
    }

    public String getMonday() {
        return monday.get();
    }

    public SimpleStringProperty mondayProperty() {
        return monday;
    }

    public String getTuesday() {
        return tuesday.get();
    }

    public SimpleStringProperty tuesdayProperty() {
        return tuesday;
    }

    public String getWednesday() {
        return wednesday.get();
    }

    public SimpleStringProperty wednesdayProperty() {
        return wednesday;
    }

    public String getThursday() {
        return thursday.get();
    }

    public SimpleStringProperty thursdayProperty() {
        return thursday;
    }

    public String getFriday() {
        return friday.get();
    }

    public SimpleStringProperty fridayProperty() {
        return friday;
    }

    public String getSaturday() {
        return saturday.get();
    }

    public SimpleStringProperty saturdayProperty() {
        return saturday;
    }

    public String getSunday() {
        return sunday.get();
    }

    public SimpleStringProperty sundayProperty() {
        return sunday;
    }
}
