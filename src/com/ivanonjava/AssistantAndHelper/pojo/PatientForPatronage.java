package com.ivanonjava.AssistantAndHelper.pojo;

import javafx.beans.property.SimpleStringProperty;

public class PatientForPatronage extends PatientForDocument {

    private SimpleStringProperty oneMonth;
    private SimpleStringProperty twoMonth;
    private SimpleStringProperty threeMonth;
    private SimpleStringProperty fourMonth;
    private SimpleStringProperty fiveMonth;
    private SimpleStringProperty sixMonth;
    private SimpleStringProperty sevenMonth;
    private SimpleStringProperty eightMonth;
    private SimpleStringProperty nineMonth;
    private SimpleStringProperty tenMonth;
    private SimpleStringProperty elevenMonth;

    public PatientForPatronage(int id, String FIO, String birthday, String discardDay, String address, String k, String phone, boolean isPresent, String comment, String oneDay, String threeDay, String twoWeek, String threeWeek, String oneMonth, String twoMonth, String threeMonth, String fourMonth, String fiveMonth, String sixMonth, String sevenMonth, String eightMonth, String nineMonth, String tenMonth, String elevenMonth) {
        super(id, FIO, birthday, discardDay, address, k, phone, isPresent, comment, oneDay, threeDay, twoWeek, threeWeek);
        this.oneMonth = new SimpleStringProperty(replaceDate(oneMonth));
        this.twoMonth = new SimpleStringProperty(replaceDate(twoMonth));
        this.threeMonth = new SimpleStringProperty(replaceDate(threeMonth));
        this.fourMonth = new SimpleStringProperty(replaceDate(fourMonth));
        this.fiveMonth = new SimpleStringProperty(replaceDate(fiveMonth));
        this.sixMonth = new SimpleStringProperty(replaceDate(sixMonth));
        this.sevenMonth = new SimpleStringProperty(replaceDate(sevenMonth));
        this.eightMonth = new SimpleStringProperty(replaceDate(eightMonth));
        this.nineMonth = new SimpleStringProperty(replaceDate(nineMonth));
        this.tenMonth = new SimpleStringProperty(replaceDate(tenMonth));
        this.elevenMonth = new SimpleStringProperty(replaceDate(elevenMonth));
    }

    public String getOneMonth() {
        return oneMonth.get();
    }

    public SimpleStringProperty oneMonthProperty() {
        return oneMonth;
    }

    public void setOneMonth(String oneMonth) {
        this.oneMonth.set(oneMonth);
    }

    public String getTwoMonth() {
        return twoMonth.get();
    }

    public SimpleStringProperty twoMonthProperty() {
        return twoMonth;
    }

    public void setTwoMonth(String twoMonth) {
        this.twoMonth.set(twoMonth);
    }

    public String getThreeMonth() {
        return threeMonth.get();
    }

    public SimpleStringProperty threeMonthProperty() {
        return threeMonth;
    }

    public void setThreeMonth(String threeMonth) {
        this.threeMonth.set(threeMonth);
    }

    public String getFourMonth() {
        return fourMonth.get();
    }

    public SimpleStringProperty fourMonthProperty() {
        return fourMonth;
    }

    public void setFourMonth(String fourMonth) {
        this.fourMonth.set(fourMonth);
    }

    public String getFiveMonth() {
        return fiveMonth.get();
    }

    public SimpleStringProperty fiveMonthProperty() {
        return fiveMonth;
    }

    public void setFiveMonth(String fiveMonth) {
        this.fiveMonth.set(fiveMonth);
    }

    public String getSixMonth() {
        return sixMonth.get();
    }

    public SimpleStringProperty sixMonthProperty() {
        return sixMonth;
    }

    public void setSixMonth(String sixMonth) {
        this.sixMonth.set(sixMonth);
    }

    public String getSevenMonth() {
        return sevenMonth.get();
    }

    public SimpleStringProperty sevenMonthProperty() {
        return sevenMonth;
    }

    public void setSevenMonth(String sevenMonth) {
        this.sevenMonth.set(sevenMonth);
    }

    public String getEightMonth() {
        return eightMonth.get();
    }

    public SimpleStringProperty eightMonthProperty() {
        return eightMonth;
    }

    public void setEightMonth(String eightMonth) {
        this.eightMonth.set(eightMonth);
    }

    public String getNineMonth() {
        return nineMonth.get();
    }

    public SimpleStringProperty nineMonthProperty() {
        return nineMonth;
    }

    public void setNineMonth(String nineMonth) {
        this.nineMonth.set(nineMonth);
    }

    public String getTenMonth() {
        return tenMonth.get();
    }

    public SimpleStringProperty tenMonthProperty() {
        return tenMonth;
    }

    public void setTenMonth(String tenMonth) {
        this.tenMonth.set(tenMonth);
    }

    public String getElevenMonth() {
        return elevenMonth.get();
    }

    public SimpleStringProperty elevenMonthProperty() {
        return elevenMonth;
    }

    public void setElevenMonth(String elevenMonth) {
        this.elevenMonth.set(elevenMonth);
    }
}
