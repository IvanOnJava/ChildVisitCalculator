package com.ivanonjava.ChildVisitCalculator.pojo;

public class PatientForDiary {
    private int id;
    private String name;
    private String reason;
    private String address;
    private String phone;
    private String birthday;


    public PatientForDiary(int id, String name, String reason, String address, String phone, String birthday) {
        this.id = id;
        this.name = name;
        this.reason = replaceReason(reason);
        this.address = address;
        this.phone = phone;
        this.birthday = replaceBirthday(birthday);
    }

    private String replaceBirthday(String birthday) {
        String[] temp = birthday.split("-");
        return String.format("%s.%s.%s", temp[2], temp[1], temp[0]);
    }

    private String replaceReason(String reason) {
        switch (reason) {
            case "one_day":
                return "1 день";
            case "three_day":
                return "3 день";
            case "two_weeks":
                return "14 дней";
            case "three_weeks":
                return "21 день";
            case "one_month":
                return "1.5 мес";
            case "two_months":
                return "2.5 мес";
            case "three_months":
                return "3.5 мес";
            case "four_months":
                return "4.5 мес";
            case "five_months":
                return "5.5 мес";
            case "six_months":
                return "6.5 мес";
            case "seven_months":
                return "7.5 мес";
            case "eight_months":
                return "8.5 мес";
            case "nine_months":
                return "9.5 мес";
            case "ten_months":
                return "10.5 мес";
            case "eleven_months":
                return "11.5 мес";
            default:
                return reason;
        }
    }

    public int getHash() {
        return (name + address + birthday).hashCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " " + reason + " " + address + " " + birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
