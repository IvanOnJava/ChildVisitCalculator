package com.ivanonjava.ChildVisitCalculator.helpers;

import static com.ivanonjava.ChildVisitCalculator.Constants.*;

public class TranslateWord{

    public static String translateTableNameToDatabaseName(String word){
        switch (word){
            case NAME_TABLE_ID: return NAME_DB_ID;
            case NAME_TABLE_FULLNAME: return NAME_DB_FULLNAME;
            case NAME_TABLE_BIRTHDAY: return NAME_DB_BIRTHDAY;
            case NAME_TABLE_DISCARDDAY: return NAME_DB_DISCARDDAY;
            case NAME_TABLE_STREET: return NAME_DB_STREET;
            default: return null;
        }
    }
}
