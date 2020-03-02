package com.ivanonjava.ChildVisitCalculator.helpers;

import java.util.ArrayList;
import java.util.List;

public enum Reasons {

    MED("Медотвод"), REFUSAL("Отказ"), NOVACCINE("Нет вакцины"), NULL("");

    String reasons;

    Reasons(String reasons) {
        this.reasons = reasons;
    }

    public String getReasons(){
        return reasons;
    }

    public static ArrayList<String> getValues(){
        ArrayList<String> values = new ArrayList<>();
        for(Reasons r : Reasons.values()){
            values.add(r.reasons);
        }
        return values;
    }
}
