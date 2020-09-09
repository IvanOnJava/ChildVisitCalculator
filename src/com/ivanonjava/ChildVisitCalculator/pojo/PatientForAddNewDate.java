package com.ivanonjava.ChildVisitCalculator.pojo;

public class PatientForAddNewDate {
    private int id;
    private String one_day;

    public PatientForAddNewDate(int id, String one_day) {
        this.id = id;
        this.one_day = one_day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOne_day() {
        return one_day;
    }

    public void setOne_day(String one_day) {
        this.one_day = one_day;
    }
}
