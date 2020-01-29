package com.ivanonjava.ChildVisitCalculator;

public enum Pages {

    MAIN{
        @Override
        public String toString() {
            return Pages.getPage("main");
        }
    },
    DOCUMENT{
        @Override
        public String toString(){
            return Pages.getPage("document");
        }
    },
    HOLIDAYS{
        @Override
        public String toString(){
            return Pages.getPage("newHolidays");
        }
    };
    private static String getPage(String name){
        return Constants.TEMPLATES_URL + name.trim() + Constants.POST_F;
    }
}
