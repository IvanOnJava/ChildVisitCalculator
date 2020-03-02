package com.ivanonjava.ChildVisitCalculator.pojo;

import com.ivanonjava.ChildVisitCalculator.domains.DatabaseController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PatientForMagazine extends PatientForDocument {
    private SimpleStringProperty gender;
    private SimpleBooleanProperty sertificate;
    private SimpleStringProperty weightB;
    private SimpleStringProperty heightB;
    private SimpleStringProperty weightD;
    private SimpleStringProperty diagnose;
    private SimpleStringProperty dateNBO;
    private SimpleStringProperty dateAUDIO;
    private SimpleBooleanProperty tuber;
    private SimpleStringProperty dateBCJ;
    private SimpleStringProperty serialBCJ;
    private SimpleStringProperty dateGEP;
    private SimpleStringProperty serialGEP;
    private SimpleStringProperty roddom;
    private SimpleStringProperty helper;
    private SimpleIntegerProperty number;


    public PatientForMagazine(int id, String FIO, String birthday, String discardDay, String address, String k, String gender, String phone, boolean isPresent, String comment, String oneDay, String threeDay, String twoWeeks, String threeWeeks, boolean sertificate, String weightB, String heightB, String weightD, String diagnose, String dateNBO, String dateAUDIO, boolean tuber, String dateBCJ, String serialBCJ, String dateGEP, String serialGEP, String roddom, String helper, int number) {
        super(id, FIO, birthday, discardDay, address,k, phone, isPresent, comment, oneDay, threeDay, twoWeeks, threeWeeks);
        this.gender = new SimpleStringProperty(gender);
        this.sertificate = new SimpleBooleanProperty(sertificate);
        this.tuber = new SimpleBooleanProperty(tuber);
        this.weightB = new SimpleStringProperty(weightB);
        this.heightB = new SimpleStringProperty(heightB);
        this.weightD = new SimpleStringProperty(weightD);
        this.diagnose = new SimpleStringProperty(diagnose);
        this.dateNBO = new SimpleStringProperty(dateNBO);
        this.dateAUDIO = new SimpleStringProperty(dateAUDIO);
        this.dateBCJ = new SimpleStringProperty(dateBCJ);
        this.serialBCJ = new SimpleStringProperty(serialBCJ);
        this.dateGEP = new SimpleStringProperty(dateGEP);
        this.serialGEP = new SimpleStringProperty(serialGEP);
        this.roddom = new SimpleStringProperty(roddom);
        this.helper = new SimpleStringProperty(helper);
        this.number = new SimpleIntegerProperty(number);
    }

    public boolean isSertificate() {
        return sertificate.get();
    }

    public SimpleBooleanProperty sertificateProperty() {
        return sertificate;
    }

    public void setSertificate(boolean sertificate) {
        if(DatabaseController.updatePatientSertificateOnId(getId(), sertificate))
        this.sertificate.set(sertificate);
    }

    public boolean isTuber() {
        return tuber.get();
    }

    public SimpleBooleanProperty tuberProperty() {
        return tuber;
    }

    public void setTuber(boolean tuber) {
        if(DatabaseController.updatePatientTuberOnId(getId(), tuber))
            this.tuber.set(tuber);
    }

    public String getWeightB() {
        return weightB.get();
    }

    public SimpleStringProperty weightBProperty() {
        return weightB;
    }

    public void setWeightB(String weightB) {
        if (DatabaseController.updatePatientWeightB(getId(), weightB))
            this.weightB.set(weightB);
    }


    public String getHeightB() {
        return heightB.get();
    }

    public SimpleStringProperty heightBProperty() {
        return heightB;
    }

    public void setHeightB(String heightB) {
        if (DatabaseController.updatePatientHeightB(getId(), heightB))
            this.heightB.set(heightB);
    }

    public String getWeightD() {
        return weightD.get();
    }

    public SimpleStringProperty weightDProperty() {
        return weightD;
    }

    public void setWeightD(String weightD) {
        if (DatabaseController.updatePatientWeightD(getId(), weightD))
            this.weightD.set(weightD);
    }

    public String getDateNBO() {
        return dateNBO.get();
    }

    public SimpleStringProperty dateNBOProperty() {
        return dateNBO;
    }

    public void setDateNBO(String dateNBO) {
        if (DatabaseController.updatePatientDateNBO(getId(), dateNBO))
            this.dateNBO.set(dateNBO);
    }

    public String getDateAUDIO() {
        return dateAUDIO.get();
    }

    public SimpleStringProperty dateAUDIOProperty() {
        return dateAUDIO;
    }

    public void setDateAUDIO(String dateAUDIO) {
        if (DatabaseController.updatePatientDateAUDIO(getId(), dateAUDIO))
            this.dateAUDIO.set(dateAUDIO);
    }

    public String getDateBCJ() {
        return dateBCJ.get();
    }

    public SimpleStringProperty dateBCJProperty() {
        return dateBCJ;
    }

    public void setDateBCJ(String dateBCJ) {
        if (DatabaseController.updatePatientDateBCJ(getId(), dateBCJ))
            this.dateBCJ.set(dateBCJ);
    }

    public String getSerialBCJ() {
        return serialBCJ.get();
    }

    public SimpleStringProperty serialBCJProperty() {
        return serialBCJ;
    }

    public void setSerialBCJ(String serialBCJ) {
        if (DatabaseController.updatePatientSerialBCJ(getId(), serialBCJ))
            this.serialBCJ.set(serialBCJ);
    }

    public String getDateGEP() {

        return dateGEP.get();
    }

    public SimpleStringProperty dateGEPProperty() {
        return dateGEP;
    }

    public void setDateGEP(String dateGEP) {
        if (DatabaseController.updatePatientDateGEP(getId(), dateGEP))
            this.dateGEP.set(dateGEP);
    }

    public String getSerialGEP() {
        return serialGEP.get();
    }

    public SimpleStringProperty serialGEPProperty() {
        return serialGEP;
    }

    public void setSerialGEP(String serialGEP) {
        if (DatabaseController.updatePatientSerialGEP(getId(), serialGEP))
            this.serialGEP.set(serialGEP);
    }

    public String getRoddom() {
        return roddom.get();
    }

    public SimpleStringProperty roddomProperty() {
        return roddom;
    }

    public void setRoddom(String roddom) {
        if (DatabaseController.updatePatientRoddom(getId(), roddom))
            this.roddom.set(roddom);
    }

    public String getHelper() {
        return helper.get();
    }

    public SimpleStringProperty helperProperty() {
        return helper;
    }

    public void setHelper(String helper) {
        if (DatabaseController.updatePatientHelper(getId(), helper))
            this.helper.set(helper);
    }

    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public String getGender() {
        return gender.get();
    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        if(DatabaseController.updatePatientGenderOnId(getId(), gender))
        this.gender.set(gender);
    }

    public String getDiagnose() {
        return diagnose.get();
    }

    public SimpleStringProperty diagnoseProperty() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        if (DatabaseController.updatePatientDiagnoseOnId(getId(), diagnose))
            this.diagnose.set(diagnose);
    }
}
