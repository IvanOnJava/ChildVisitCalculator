package com.ivanonjava.ChildVisitCalculator.pojo;

public class PatientForDelta {
    private int id;
    private String birthday;
    private String discardday;
    private int id_adr;

    public PatientForDelta(int id, String birthday, String discardday, int id_adr) {
        this.id = id;
        this.birthday = birthday;
        this.discardday = discardday;
        this.id_adr = id_adr;
    }

    public int getId() {
        return id;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDiscardday() {
        return discardday;
    }

    public int getId_adr() {
        return id_adr;
    }

    @Override
    public String toString() {
        return "PatientForDelta{" +
                "id=" + id +
                ", birthday='" + birthday + '\'' +
                ", discardday='" + discardday + '\'' +
                ", id_adr=" + id_adr +
                '}';
    }
}
