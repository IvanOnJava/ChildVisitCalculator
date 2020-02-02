package com.ivanonjava.ChildVisitCalculator.helpers;

public final class Constants {
    public final String AUTHOR = "Saint-Petersburg©Ivan Povoyko 2019";
    public final String LANGUAGE = "Java 1.8.0_221 SE";
    public final String VERSION_NAME = "2.0.0";
    public final String EMAIL = "ivanonjava@yandex.ru";
    public final int MAX_PATIENTS = 8;
    public final String DB_URL = "jdbc:sqlite:";
    public final String DB_NAME = "BD.db";
    public final String DB_Driver = "org.sqlite.JDBC";
    public final String DOCUMENT_FILE_PATH = System.getProperty("user.home") + "/Desktop/Patron/";
    public final String PATH_ICON_ADDRESS = "com/ivanonjava/ChildVisitCalculator/UI/templates/css/images/addresses.png";
    public final String PATH_ICON_RELOAD = "com/ivanonjava/ChildVisitCalculator/UI/templates/css/images/reload.png";
    public final String PATH_ICON_INFO = "com/ivanonjava/ChildVisitCalculator/UI/templates/css/images/icoInfo.png";

    public final String PATH_ICON_ADD_PATIENT = "com/ivanonjava/ChildVisitCalculator/UI/templates/css/images/addPatient.png";
    public final String DATE_PATTERN = "dd.MM.yyyy";
    public final String TITLE = "Child Visit Calculator ";
    public final double MAIN_PAGE_MIN_WIDTH = 300;
    public final double MAIN_PAGE_MIN_HEIGHT = 400;
    public final String PATH_ICON_MAIN = "com/ivanonjava/ChildVisitCalculator/UI/templates/css/images/ic.png";
    public final double DOCUMENT_PAGE_MIN_WIDTH = 700;
    public final double DOCUMENT_PAGE_MIN_HEIGHT = 400;


    public static final String NAME_TABLE_ID = "№ п/п";
    public final String NAME_TABLE_BUTTON = "-";
    public static final String NAME_TABLE_FULLNAME = "ФИО";
    public static final String NAME_TABLE_BIRTHDAY = "Дата рождения";
    public static final String NAME_TABLE_DISCARDDAY = "Дата выписки";
    public final String NAME_TABLE_COMMENT = "Комментарий";
    public final String NAME_TABLE_PRESENT = "Выбыл";
    public final String NAME_TABLE_ADDRESS = "Адрес";
    public static final String NAME_TABLE_STREET = "Улица";
    public final String NAME_TABLE_APARTMENT = "кв";
    public final String NAME_TABLE_PHONE = "Телефон";
    public final String NAME_TABLE_ONE_DAY = "1 день";
    public final String NAME_TABLE_THREE_DAY = "3 день";
    public final String NAME_TABLE_TWO_WEEKS = "14 день";
    public final String NAME_TABLE_THREE_WEEKS = "21 день";
    public final String NAME_TABLE_ONE_MONTHS = "1.5 мес";
    public final String NAME_TABLE_TWO_MONTHS = "2.5 мес";
    public final String NAME_TABLE_THREE_MONTHS = "3.5 мес";
    public final String NAME_TABLE_FOUR_MONTHS = "4.5 мес";
    public final String NAME_TABLE_FIVE_MONTHS = "5.5 мес";
    public final String NAME_TABLE_SIX_MONTHS = "6.5 мес";
    public final String NAME_TABLE_SEVEN_MONTHS = "7.5 мес";
    public final String NAME_TABLE_EIGHT_MONTHS = "8.5 мес";
    public final String NAME_TABLE_NINE_MONTHS = "9.5 мес";
    public final String NAME_TABLE_TEN_MONTHS = "10.5 мес";
    public final String NAME_TABLE_ELEVEN_MONTHS = "11.5 мес";
    public final String NAME_TABLE_SERTIFICATE = "Сертификат";
    public final String NAME_TABLE_WEIGHT_BIRTHDAY = "Вес при рождении";
    public final String NAME_TABLE_HEIGHT_BIRTHDAY = "Рост при рождении";
    public final String NAME_TABLE_WEIGHT_DISCARDDAY = "Вес при выписке";
    public final String NAME_TABLE_NBO = "НБО";
    public final String NAME_TABLE_AUDIO = "АУДИОСКРИННИНГ";
    public final String NAME_TABLE_TUBER = "Обследование на туберкулез";
    public final String NAME_TABLE_BCJ = "БЦЖ";
    public final String NAME_TABLE_SERIAL_BJC = "БЦЖ серия";
    public final String NAME_TABLE_GEP = "ГЕПАТИТ";
    public final String NAME_TABLE_SERIAL_GEP = "ГЕПАТИТ серия";
    public final String NAME_TABLE_RODDOM = "РодДом";
    public final String NAME_TABLE_HELPER = "Кто передал";
    public final String NAME_TABLE_NUMBER = "номер участка";
    public final String NAME_TABLE_GENDER = "Пол";
    public final String NAME_TABLE_DIA = "Диагноз";

    public final String NAME_DB_ID = "id";
    public final String NAME_DB_FULLNAME = "fullname";
    public final String NAME_DB_BIRTHDAY = "birthday";
    public final String NAME_DB_DISCARDDAY = "discardday";
    public final String NAME_DB_STREET = "address";

    public final String TEMPLATES_URL = "UI/templates/";
    public final String POST_F = "Page.fxml";
    public final String TAB_MAGAZINE_NAME = "Журнал новорождёных";
    public final String TAB_PATRONAGE_NAME = "Патронаж №";
    public final String TOOLTIP_SERIAL_PATIENTPAGE = "Укажите серию, ключевое слово [Медотвод, Отказ] или оставьте пустым";
    private static Constants instance;

    private Constants() {


    }

    public static Constants getInstance() {
        if (instance == null) {
            instance = new Constants();
        }
        return instance;
    }

}
