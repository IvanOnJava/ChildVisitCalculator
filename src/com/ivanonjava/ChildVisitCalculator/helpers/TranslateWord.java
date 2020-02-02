package com.ivanonjava.ChildVisitCalculator.helpers;

import static com.ivanonjava.ChildVisitCalculator.helpers.Constants.getInstance;
public class TranslateWord{

    public static String translateTableNameToDatabaseName(String word){
       
        switch (word){
            case Constants.NAME_TABLE_ID: return getInstance().NAME_DB_ID;
            case Constants.NAME_TABLE_FULLNAME: return getInstance().NAME_DB_FULLNAME;
            case Constants.NAME_TABLE_BIRTHDAY: return getInstance().NAME_DB_BIRTHDAY;
            case Constants.NAME_TABLE_DISCARDDAY: return getInstance().NAME_DB_DISCARDDAY;
            case Constants.NAME_TABLE_STREET: return getInstance().NAME_DB_STREET;
            default: return null;
        }
    }
}
