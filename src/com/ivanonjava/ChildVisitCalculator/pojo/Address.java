package com.ivanonjava.ChildVisitCalculator.pojo;

import com.ivanonjava.ChildVisitCalculator.domains.DatabaseController;
import javafx.beans.property.SimpleStringProperty;

public class Address {
    private SimpleStringProperty address;
    private SimpleStringProperty number;

    public Address(String address, String number) {
        this.address = new SimpleStringProperty(address);
        this.number = new SimpleStringProperty(number);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        if (DatabaseController.updateAddress(getAddress(), address))
            this.address.set(address);
    }

    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        if (DatabaseController.updateAddressNumber(getAddress(), number))
            this.number.set(number);
    }

    public void deleteAddress() {
        DatabaseController.deleteAddress(getAddress());
    }
}
