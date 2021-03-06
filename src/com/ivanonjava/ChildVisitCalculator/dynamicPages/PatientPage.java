package com.ivanonjava.ChildVisitCalculator.dynamicPages;

import com.ivanonjava.ChildVisitCalculator.UI.controllers.DocumentPageControllers;
import com.ivanonjava.ChildVisitCalculator.domains.DatabaseController;
import com.ivanonjava.ChildVisitCalculator.helpers.Constants;
import com.ivanonjava.ChildVisitCalculator.helpers.HelperForDatepicker;
import com.ivanonjava.ChildVisitCalculator.helpers.Reasons;
import com.ivanonjava.ChildVisitCalculator.pojo.Patient;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PatientPage extends Stage {
    private TextField fullName;

    private TextField address_text;
    private MenuButton addresses;
    private TextField kv;

    private TextField phone;

    private DatePicker birthday;
    private TextField birthdayWeight;
    private TextField birthdayHeight;

    private DatePicker discardDay;
    private TextField discardWeight;

    private TextField diagnose;

    private DatePicker dateNBO;

    private DatePicker dateAUDIO;

    private DatePicker dateBCJ;
    private TextField serialBCJ;
    private MenuButton reasonsBCJ;

    private DatePicker dateGEP;
    private TextField serialGEP;
    private MenuButton reasonsGEP;

    private CheckBox tuber;

    private TextField roddom;

    private TextField helper;

    private Button buttonAdd;

    private MenuButton gender;
    private void configButton() {
        fullName = getTextField("ФИО");
        address_text = getTextField("Адрес");
        address_text.setEditable(false);
        addresses = new MenuButton();
        addresses.getItems().addAll(getAddressesItems(address_text));
        addresses.setText("...");
        kv = getTextField("кв");
        kv.setMaxWidth(50);

        phone = getTextField("Телефон");

        birthday = getDatePicker("Дата рождения");
        gender = new MenuButton();
        gender.getItems().addAll(getGenders());

        birthdayWeight = getTextField("Вес при рождении(г)");

        birthdayHeight = getTextField("Рост при рождении(см)");

        discardDay = getDatePicker("Дата выписки");

        discardWeight = getTextField("Вес при выписке(г)");

        diagnose = getTextField("Диагноз");

        dateNBO = getDatePicker("Дата НБО");

        dateAUDIO = getDatePicker("Дата АУДИОСКРИНИНГА");

        dateBCJ = getDatePicker("Дата БЦЖ");
        serialBCJ = getTextField("Серия БЦЖ");
        serialBCJ.setTooltip(tooltipSerial);
        reasonsBCJ = new MenuButton();
        reasonsBCJ.getItems().addAll(getReasonsItems(serialBCJ));
        reasonsBCJ.setText("...");

        dateGEP = getDatePicker("Дата гепатита");
        serialGEP = getTextField("Серия гепатита");
        serialGEP.setTooltip(tooltipSerial);
        reasonsGEP = new MenuButton();
        reasonsGEP.getItems().addAll(getReasonsItems(serialGEP));
        reasonsGEP.setText("...");

        tuber = new CheckBox();

        roddom = getTextField("Роддом");

        helper = getTextField("Кто передал");

        buttonAdd = new Button("Добавить");
        buttonAdd.setOnAction(event -> {
            if (fullName.getText().length() > 0 &&
                    address_text.getText().length() > 2 &&
                    birthday.getEditor().getText().length() > 5 &&
                    discardDay.getEditor().getText().length() > 5 && !gender.getText().trim().equalsIgnoreCase("") && check(serialBCJ.getText()) && check(serialGEP.getText())) {
                DatabaseController.addPatient(
                        new Patient(
                                getText(fullName),
                                getText(address_text),
                                getText(kv),
                                getText(phone),
                                getText(birthday.getEditor()),
                                getText(birthdayWeight),
                                getText(birthdayHeight),
                                getText(discardDay.getEditor()),
                                getText(discardWeight),
                                gender.getText().trim(),
                                getText(dateNBO.getEditor()),
                                getText(dateAUDIO.getEditor()),
                                getText(dateBCJ.getEditor()),
                                getText(serialBCJ),
                                getText(dateGEP.getEditor()),
                                getText(serialGEP),
                                tuber.isSelected(),
                                getText(roddom),
                                getText(helper),
                                getText(diagnose)
                        )
                );
                tuber.setSelected(false);
                gender.setText("");
                DocumentPageControllers.update();
                DocumentPageControllers.reopenAddPatientPage();
            }
        });

    }

    boolean check(String text){
        boolean c = DocumentPageControllers.checkSerial(text);
        if(!c){
            Point2D point = serialBCJ.localToScreen(serialBCJ.getLayoutBounds().getMaxX(), serialBCJ.getLayoutBounds().getMaxY());
            serialBCJ.setTooltip(tooltipSerial);
            if(!tooltipSerial.isShowing())
               tooltipSerial.show(serialBCJ, point.getX(), point.getY());
        }
        return c;
    }


    private <T extends TextInputControl> String getText(T node) {
        return node.getText().trim();
    }
    Tooltip tooltipSerial;
    public PatientPage() {
        tooltipSerial = new Tooltip(   Constants.getInstance().TOOLTIP_SERIAL_PATIENTPAGE);

        getIcons().add(new Image(   Constants.getInstance().PATH_ICON_ADD_PATIENT));
        configButton();
        VBox vBoxLeft = new VBox();
        vBoxLeft.getChildren().addAll(
                new Separator(),
                getControl("ФИО                 ", fullName),
                new Separator(),
                getControl("Адрес ", address_text, addresses, kv),
                new Separator(),
                getControl("Дата рождения       ", birthday),
                new Separator(),
                getControl("Дата выписки        ", discardDay),
                new Separator(),
                getControl("Пол                 ", gender),
                new Text("\nДополнительная информация:\t"),
                new Separator(),
                getControl("Телефон             ", phone),
                new Separator(),
                getControl("Вес при рождении    ", birthdayWeight),
                new Separator(),
                getControl("Рост при рождении   ", birthdayHeight),
                new Separator(),
                getControl("Вес при выписке     ", discardWeight),
                new Separator(),
                getControl("Диагноз             ", diagnose),
                new Separator(),
                getControl("Дата НБО            ", dateNBO),
                new Separator(),
                getControl("Дата АУДИОСКРИНИНГА ", dateAUDIO),
                new Separator(),
                getControl("БЦЖ ", dateBCJ, serialBCJ, reasonsBCJ),
                new Separator(),
                getControl("ГЕПАТИТ ", dateGEP, serialGEP, reasonsGEP),
                new Separator(),
                getControl("Туберкулез   ", tuber),
                new Separator(),
                getControl("РОДДОМ      ", roddom),
                new Separator(),
                getControl("Кто передал ", helper),
                new Separator(),
                getControl("", buttonAdd)
        );
        vBoxLeft.setSpacing(1.0);

        fullName.setMinWidth(220);
        address_text.setMinWidth(220);
        roddom.setMinWidth(220);
        helper.setMinWidth(220);
        Scene scene = new Scene(vBoxLeft);
        setMinHeight(600);
        setMinWidth(400);
        buttonAdd.setAlignment(Pos.CENTER);
        setResizable(false);
        setScene(scene);

    }



    private HBox getControl(String text, Node... nodes) {
        HBox hBox = new HBox();
        if(text.equals(""))
            hBox.setAlignment(Pos.CENTER);
        else
            hBox.setAlignment(Pos.TOP_LEFT);
        hBox.getChildren().add(new Text("  "));
        hBox.getChildren().add(new Text(text));
        hBox.getChildren().addAll(nodes);
        return hBox;
    }

    private TextField getTextField(String name) {
        TextField field = new TextField();
        field.setPromptText(name);
        return field;
    }

    private DatePicker getDatePicker(String name) {
        DatePicker picker = new DatePicker();
        picker.setEditable(true);
        picker.setPromptText(name);
        picker.setConverter(HelperForDatepicker.getConverter());
        picker.getEditor().setTextFormatter(HelperForDatepicker.getFormatter());
        return picker;
    }

    private MenuItem[] getAddressesItems(TextField e) {
        ArrayList<String> adr = DatabaseController.getAddresses();
        return getMenuItems(e, adr);
    }

    private MenuItem[] getReasonsItems(TextField e){
        ArrayList<String> reasons = Reasons.getValues();
        return getMenuItems(e, reasons);
    }

    private MenuItem[] getMenuItems(TextField e, ArrayList<String> reasons) {
        MenuItem[] items = new MenuItem[reasons.size()];
        for (int i = 0; i < reasons.size(); i++) {
            MenuItem item = new MenuItem(reasons.get(i));
            item.setOnAction(event -> e.setText(item.getText()));
            items[i] = item;

        }
        return items;
    }

    private MenuItem[] getGenders() {
        MenuItem[] items = new MenuItem[2];
        items[0] = new MenuItem("M");
        items[1] = new MenuItem("Ж");
        for (int i = 0; i < items.length; i++) {
            int finalI = i;
            items[i].setOnAction(event -> gender.setText(items[finalI].getText()));
        }
        return items;
    }
}
