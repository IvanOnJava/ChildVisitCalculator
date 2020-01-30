package com.ivanonjava.ChildVisitCalculator.helpers;

public enum Reasons {
    MED("Медотвод"), REFUSAL("Отказ"), NULL("");
    String reasons;
    Reasons(String reasons) {
        this.reasons = reasons;
    }
    public String getReasons(){
        return reasons;
    }
}
