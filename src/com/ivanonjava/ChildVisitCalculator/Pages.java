package com.ivanonjava.ChildVisitCalculator;

public enum Pages {

    MAIN,
    DOCUMENT,
    newHOLIDAYS;
    static String getPage(Pages pages){
        return Constants.TEMPLATES_URL + pages.name() + Constants.POST_F;
    }
}
