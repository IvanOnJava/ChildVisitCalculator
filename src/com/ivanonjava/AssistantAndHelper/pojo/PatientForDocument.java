package com.ivanonjava.AssistantAndHelper.pojo;

import com.ivanonjava.AssistantAndHelper.domains.DatabaseController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class PatientForDocument {
    private SimpleIntegerProperty id;
    private SimpleStringProperty FIO;
    private SimpleStringProperty birthday;
    private SimpleStringProperty discardDay;
    private SimpleStringProperty address;
    private SimpleStringProperty kv;
    private SimpleStringProperty phone;
    private SimpleBooleanProperty isPresent;
    private SimpleStringProperty comment;
    private SimpleStringProperty oneDay;
    private SimpleStringProperty threeDay;
    private SimpleStringProperty twoWeeks;
    private SimpleStringProperty threeWeeks;

    PatientForDocument(int id, String FIO, String birthday, String discardDay, String address, String kv, String phone, boolean isPresent, String comment, String oneDay, String threeDay, String twoWeek, String threeWeek) {
        this.id = new SimpleIntegerProperty(id);
        this.FIO = new SimpleStringProperty(FIO);
        this.birthday = new SimpleStringProperty(replaceDate(birthday));
        this.discardDay = new SimpleStringProperty(replaceDate(discardDay));
        this.address = new SimpleStringProperty(address);
        this.kv = new SimpleStringProperty(kv);
        this.phone = new SimpleStringProperty(phone);
        this.isPresent = new SimpleBooleanProperty(isPresent);
        this.comment = new SimpleStringProperty(comment);
        this.oneDay = new SimpleStringProperty(replaceDate(oneDay));
        this.threeDay = new SimpleStringProperty(replaceDate(threeDay));
        this.twoWeeks = new SimpleStringProperty(replaceDate(twoWeek));
        this.threeWeeks = new SimpleStringProperty(replaceDate(threeWeek));
    }
    String replaceDate(String date) {
        String[] temp = date.split("-");
        return String.format("%s.%s.%s", temp[2], temp[1], temp[0]);
    }

    public void deletePatient(){
        DatabaseController.deletePatient(this.getId());
    }
    @Override
    public int hashCode() {
        return (getFIO() + getAddress() + getBirthday() + getPhone()).hashCode();
    }


    @Override
    public String toString() {
        return getFIO() + " "
                + getPhone() + " "
                + getAddress() + " "
                + getBirthday();
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }



    public String getFIO() {
        return FIO.get();
    }

    public SimpleStringProperty FIOProperty() {
        return FIO;
    }

    public void setFIO(String FIO) {
        DatabaseController.updatePatientNameOnId(getId(), FIO);
        this.FIO.set(FIO);
    }

    public String getBirthday() {
        return birthday.get();
    }

    public SimpleStringProperty birthdayProperty() {
        return birthday;
    }



    public String getDiscardDay() {
        return discardDay.get();
    }

    public SimpleStringProperty discardDayProperty() {
        return discardDay;
    }


    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        DatabaseController.updatePatientAddressOnId(getId(), address);
        this.address.set(address);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        DatabaseController.updatePatientPhoneOnId(getId(), phone);
        this.phone.set(phone);
    }

    public boolean isIsPresent() {
        return isPresent.get();
    }

    public SimpleBooleanProperty isPresentProperty() {
        return isPresent;
    }

    public void setIsPresent(boolean isPresent) {
        DatabaseController.updatePatientIsPresentOnId(getId(), isPresent);
        this.isPresent.set(isPresent);
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        DatabaseController.updatePatientCommentOnId(getId(), comment);
        this.comment.set(comment);
    }

    public String getOneDay() {
        return oneDay.get();
    }

    public SimpleStringProperty oneDayProperty() {
        return oneDay;
    }
    public void setOneDay(String oneDay) {
        if(DatabaseController.updatePatientOneDay(getId(), oneDay))
            this.oneDay.set(oneDay);
    }


    public String getThreeDay() {
        return threeDay.get();
    }

    public SimpleStringProperty threeDayProperty() {
        return threeDay;
    }



    public String getTwoWeeks() {
        return twoWeeks.get();
    }

    public SimpleStringProperty twoWeeksProperty() {
        return twoWeeks;
    }

    public String getThreeWeeks() {
        return threeWeeks.get();
    }

    public SimpleStringProperty threeWeeksProperty() {
        return threeWeeks;
    }


    public String getKv() {
        return kv.get();
    }

    public SimpleStringProperty kvProperty() {
        return kv;
    }

    public void setKv(String kv) {
        if(DatabaseController.updatePatientK(getId(), kv))
            this.kv.set(kv);
    }

    public void setThreeDay(String threeDay) {
        if(DatabaseController.updatePatientThreeDay(getId(), threeDay))
            this.threeDay.set(threeDay);
    }

    public void setTwoWeeks(String twoWeeks) {
        if(DatabaseController.updatePatientTwoWeeks(getId(), twoWeeks))
         this.twoWeeks.set(twoWeeks);
    }

    public void setThreeWeeks(String threeWeeks) {
        if(DatabaseController.updatePatientThreeWeeks(getId(), threeWeeks))
         this.threeWeeks.set(threeWeeks);
    }
}
