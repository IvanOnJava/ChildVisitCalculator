package com.ivanonjava.AssistantAndHelper.pojo;

public class Patient {

    private String fullName;
    private String address;
    private  String kv;
    private String phone;
    private String birthday;
    private String weightB;
    private String heightB;
    private String discardDay;
    private String weightD;
    private String gender;
    private String dateNBO;
    private String dateAUDIO;
    private String dateBCJ;
    private String serialBCJ;
    private String dateGEP;
    private String serialGEP;
    private boolean tuber;
    private String roddom;
    private String helper;
    private String diagnose;

    public Patient(String fullName, String address, String kv, String phone, String birthday, String weightB, String heightB, String discardDay, String weightD, String gender, String dateNBO, String dateAUDIO, String dateBCJ, String serialBCJ, String dateGEP, String serialGEP, boolean tuber, String roddom, String helper, String diagnose) {
        this.fullName = fullName;
        this.address = address;
        this.kv = kv;
        this.phone = phone;
        this.birthday = birthday;
        this.weightB = weightB;
        this.heightB = heightB;
        this.discardDay = discardDay;
        this.weightD = weightD;
        this.gender = gender;
        this.dateNBO = dateNBO;
        this.dateAUDIO = dateAUDIO;
        this.dateBCJ = dateBCJ;
        this.serialBCJ = serialBCJ;
        this.dateGEP = dateGEP;
        this.serialGEP = serialGEP;
        this.tuber = tuber;
        this.roddom = roddom;
        this.helper = helper;
        this.diagnose = diagnose;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getWeightB() {
        return weightB;
    }

    public String getHeightB() {
        return heightB;
    }

    public String getDiscardDay() {
        return discardDay;
    }

    public String getWeightD() {
        return weightD;
    }

    public String getGender() {
        return gender;
    }

    public String getDateNBO() {
        return dateNBO;
    }

    public String getDateAUDIO() {
        return dateAUDIO;
    }

    public String getDateBCJ() {
        return dateBCJ;
    }

    public String getSerialBCJ() {
        return serialBCJ;
    }

    public String getDateGEP() {
        return dateGEP;
    }

    public String getSerialGEP() {
        return serialGEP;
    }

    public boolean isTuber() {
        return tuber;
    }

    public String getRoddom() {
        return roddom;
    }

    public String getHelper() {
        return helper;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public String getKv() {
        return kv;
    }

    @Override
    public String toString() {
        return getFullName() + " " + getBirthday();
    }
}
