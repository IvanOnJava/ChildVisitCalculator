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

    public Week() {
        this.monday = new SimpleStringProperty("");
        this.tuesday = new SimpleStringProperty("");
        this.wednesday = new SimpleStringProperty("");
        this.thursday = new SimpleStringProperty("");
        this.friday = new SimpleStringProperty("");
        this.saturday = new SimpleStringProperty("");
        this.sunday = new SimpleStringProperty("");
    }

    @Override
    public String toString() {
        return "Week{" +
                "monday=" + getMonday() +
                ", tuesday=" + getTuesday() +
                ", wednesday=" + getWednesday() +
                ", thursday=" + getThursday() +
                ", friday=" + getFriday() +
                ", saturday=" + getSaturday() +
                ", sunday=" + getSunday() +
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

    public void setMonday(String monday) {
        this.monday.set(monday);
    }

    public void setTuesday(String tuesday) {
        this.tuesday.set(tuesday);
    }

    public void setWednesday(String wednesday) {
        this.wednesday.set(wednesday);
    }

    public void setThursday(String thursday) {
        this.thursday.set(thursday);
    }

    public void setFriday(String friday) {
        this.friday.set(friday);
    }

    public void setSaturday(String saturday) {
        this.saturday.set(saturday);
    }

    public void setSunday(String sunday) {
        this.sunday.set(sunday);
    }

    public void setMonday(int monday) {
        this.monday.set(String.valueOf(monday));
    }

    public void setTuesday(int tuesday) {
        this.tuesday.set(String.valueOf(tuesday));
    }

    public void setWednesday(int wednesday) {
        this.wednesday.set(String.valueOf(wednesday));
    }

    public void setThursday(int thursday) {
        this.thursday.set(String.valueOf(thursday));
    }

    public void setFriday(int friday) {
        this.friday.set(String.valueOf(friday));
    }

    public void setSaturday(int saturday) {
        this.saturday.set(String.valueOf(saturday));
    }

    public void setSunday(int sunday) {
        this.sunday.set(String.valueOf(sunday));
    }
}
