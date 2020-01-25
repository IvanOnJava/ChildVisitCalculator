package com.ivanonjava.AssistantAndHelper.dynamicPages;

import com.ivanonjava.AssistantAndHelper.Constants;
import com.ivanonjava.AssistantAndHelper.UI.controllers.DocumentPageControllers;
import com.ivanonjava.AssistantAndHelper.domains.DatabaseController;
import com.ivanonjava.AssistantAndHelper.domains.FileController;
import com.ivanonjava.AssistantAndHelper.domains.LogController;
import com.ivanonjava.AssistantAndHelper.pojo.PatientForMagazine;
import com.ivanonjava.AssistantAndHelper.pojo.PatientForPatronage;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public final class MagazineTable extends DocumentTable<PatientForMagazine> {
    private TableColumn<PatientForMagazine, String> t_twoW = new TableColumn<>(Constants.NAME_TABLE_TWO_WEEKS);
    private TableColumn<PatientForMagazine, String> t_threeW = new TableColumn<>(Constants.NAME_TABLE_THREE_WEEKS);
    private TableColumn<PatientForMagazine, Boolean> t_sert = new TableColumn<>(Constants.NAME_TABLE_SERTIFICATE);
    private TableColumn<PatientForMagazine, String> t_weiB = new TableColumn<>(Constants.NAME_TABLE_WEIGHT_BIRTHDAY);
    private TableColumn<PatientForMagazine, String> t_heiB = new TableColumn<>(Constants.NAME_TABLE_HEIGHT_BIRTHDAY);
    private TableColumn<PatientForMagazine, String> t_weiD = new TableColumn<>(Constants.NAME_TABLE_WEIGHT_DISCARDDAY);
    private TableColumn<PatientForMagazine, String> t_NBO = new TableColumn<>(Constants.NAME_TABLE_NBO);
    private TableColumn<PatientForMagazine, String> t_AUDIO = new TableColumn<>(Constants.NAME_TABLE_AUDIO);
    private TableColumn<PatientForMagazine, Boolean> t_TUBER = new TableColumn<>(Constants.NAME_TABLE_TUBER);
    private TableColumn<PatientForMagazine, String> t_BCJ = new TableColumn<>(Constants.NAME_TABLE_BCJ);
    private TableColumn<PatientForMagazine, String> t_serialBCJ = new TableColumn<>(Constants.NAME_TABLE_SERIAL_BJC);
    private TableColumn<PatientForMagazine, String> t_GEP = new TableColumn<>(Constants.NAME_TABLE_GEP);
    private TableColumn<PatientForMagazine, String> t_serialGEP = new TableColumn<>(Constants.NAME_TABLE_SERIAL_GEP);
    private TableColumn<PatientForMagazine, String> t_rod = new TableColumn<>(Constants.NAME_TABLE_RODDOM);
    private TableColumn<PatientForMagazine, String> t_help = new TableColumn<>(Constants.NAME_TABLE_HELPER);
    private TableColumn<PatientForMagazine, Integer> t_num = new TableColumn<>(Constants.NAME_TABLE_NUMBER);
    private TableColumn<PatientForMagazine, String> t_gender = new TableColumn<>(Constants.NAME_TABLE_GENDER);
    private TableColumn<PatientForMagazine, String> t_dia = new TableColumn<>(Constants.NAME_TABLE_DIA);

    public MagazineTable() {
        LogController.write("MagazineTable::start");
        configTable();
    }


    @Override
    void configTable() {
        LogController.write("MagazineTable.configTable()");
        super.patientsList.setAll(DatabaseController.getPatientsForMagazine());
        super.configDocumentTable();
        t_twoW.setCellValueFactory(new PropertyValueFactory<>("twoWeeks"));
        t_twoW.setOnEditCommit(this::editColumnTwoWeeks);

        t_threeW.setCellValueFactory(new PropertyValueFactory<>("threeWeeks"));
        t_threeW.setOnEditCommit(this::editColumnThreeWeeks);
        setCell(t_twoW, t_threeW);
        t_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        t_gender.setCellFactory(ComboBoxTableCell.forTableColumn("М", "Ж"));

        t_gender.setOnEditCommit(this::editColumnGender);
        this.getColumns().add(t_gender);

        t_sert.setCellValueFactory(param -> {
            PatientForMagazine person = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(person.isSertificate());
            booleanProp.addListener((observable, oldValue, newValue) -> person.setSertificate(newValue));
            return booleanProp;
        });
        t_sert.setCellFactory(p -> {
            CheckBoxTableCell<PatientForMagazine, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        this.getColumns().add(t_sert);

        t_weiB.setCellValueFactory(new PropertyValueFactory<>("weightB"));
        t_weiB.setOnEditCommit(this::editColumnWeightB);

        t_heiB.setCellValueFactory(new PropertyValueFactory<>("heightB"));
        t_heiB.setOnEditCommit(this::editColumnHeightB);

        t_weiD.setCellValueFactory(new PropertyValueFactory<>("weightD"));
        t_weiD.setOnEditCommit(this::editColumnWeightD);

        t_dia.setCellValueFactory(new PropertyValueFactory<>("diagnose"));
        t_dia.setOnEditCommit(this::editColumnDiagnose);

        t_NBO.setCellValueFactory(new PropertyValueFactory<>("dateNBO"));
        t_NBO.setOnEditCommit(this::editColumnDateNBO);

        t_AUDIO.setCellValueFactory(new PropertyValueFactory<>("dateAUDIO"));
        t_AUDIO.setOnEditCommit(this::editColumnDateAUDIO);
        super.setCell(t_weiB, t_heiB, t_weiD, t_dia, t_NBO, t_AUDIO);

        t_TUBER.setCellValueFactory(param -> {
            PatientForMagazine person = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(person.isTuber());
            booleanProp.addListener((observable, oldValue, newValue) -> person.setTuber(newValue));
            return booleanProp;
        });
        t_TUBER.setCellFactory(p -> {
            CheckBoxTableCell<PatientForMagazine, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        this.getColumns().add(t_TUBER);

        t_BCJ.setCellValueFactory(new PropertyValueFactory<>("dateBCJ"));
        t_BCJ.setOnEditCommit(this::editColumnDateBCJ);

        t_serialBCJ.setCellValueFactory(new PropertyValueFactory<>("serialBCJ"));
        t_serialBCJ.setOnEditCommit(this::editColumnSerialBCJ);

        t_GEP.setCellValueFactory(new PropertyValueFactory<>("dateGEP"));
        t_GEP.setOnEditCommit(this::editColumnDateGEP);

        t_serialGEP.setCellValueFactory(new PropertyValueFactory<>("serialGEP"));
        t_serialGEP.setOnEditCommit(this::editColumnSerialGEP);

        t_rod.setCellValueFactory(new PropertyValueFactory<>("roddom"));
        t_rod.setOnEditCommit(this::editColumnRoddom);

        t_help.setCellValueFactory(new PropertyValueFactory<>("helper"));
        t_help.setOnEditCommit(this::editColumnHelper);

        t_num.setCellValueFactory(new PropertyValueFactory<>("number"));

        super.setCell(t_BCJ, t_serialBCJ, t_GEP,
                t_serialGEP, t_rod, t_help);
        this.getColumns().add(t_num);
        this.setItems(patientsList);
    }
    private void editColumnThreeWeeks(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setThreeWeeks(event.getNewValue());
        LogController.write("MagazineTable.editColumnThreeWeeks(event = " + event + ")");
        updated();
    }

    private void editColumnTwoWeeks(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setTwoWeeks(event.getNewValue());
        LogController.write("MagazineTable.editColumnTwoWeeks(event = " + event + ")");
        updated();
    }
    private void editColumnGender(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setGender(event.getNewValue());
        LogController.write("MagazineTable.editColumnGender(event = " + event + ")");
        updated();
    }

    @Override
    public void saveDocument(String begin, String end) {
        LogController.write("MagazineTable.saveDocument(begin = " + begin + ", end = " + end + ")");
        FileController.saveMagazine(begin, end);
    }


    private void editColumnDateNBO(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setDateNBO(event.getNewValue());
        LogController.write("MagazineTable.editColumnDateNBO(event = " + event + ")");
        updated();
    }

    private void editColumnDiagnose(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setDiagnose(event.getNewValue());
        LogController.write("MagazineTable.editColumnDiagnose(event = " + event + ")");
        updated();
    }

    private void editColumnDateAUDIO(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setDateAUDIO(event.getNewValue());
        LogController.write("MagazineTable.editColumnDateAUDIO(event = " + event + ")");
        updated();
    }

    private void editColumnDateBCJ(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setDateBCJ(event.getNewValue());
        LogController.write("MagazineTable.editColumnDateBCJ(event = " + event + ")");
        updated();
    }

    private void editColumnSerialBCJ(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setSerialBCJ(event.getNewValue());
        LogController.write("MagazineTable.editColumnSerialBCJ(event = " + event + ")");
        updated();
    }

    private void editColumnDateGEP(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setDateGEP(event.getNewValue());
        LogController.write("MagazineTable.editColumnDateGEP(event = " + event + ")");
        updated();
    }

    private void editColumnSerialGEP(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setSerialGEP(event.getNewValue());
        LogController.write("MagazineTable.editColumnSerialGEP(event = " + event + ")");
        updated();
    }

    private void editColumnRoddom(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setRoddom(event.getNewValue());
        LogController.write("MagazineTable.editColumnRoddom(event = " + event + ")");
        updated();
    }


    private void editColumnHelper(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setHelper(event.getNewValue());
        LogController.write("MagazineTable.editColumnHelper(event = " + event + ")");
        updated();
    }

    private void editColumnWeightD(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setWeightD(event.getNewValue());
        LogController.write("MagazineTable.editColumnWeightD(event = " + event + ")");
        updated();
    }

    private void editColumnHeightB(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setHeightB(event.getNewValue());
        LogController.write("MagazineTable.editColumnHeightB(event = " + event + ")");
        updated();
    }

    private void editColumnWeightB(TableColumn.CellEditEvent<PatientForMagazine, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setWeightB(event.getNewValue());
        LogController.write("MagazineTable.editColumnWeightB(event = " + event + ")");
        updated();
    }

    @Override
    public void updateTable() {
        LogController.write("MagazineTable.updateTable()");
        String search = DocumentPageControllers.search;
        String begin = DocumentPageControllers.beginDate;
        String end = DocumentPageControllers.endDate;
        boolean check = DocumentPageControllers.check;
        if (!search.equalsIgnoreCase("null") && begin.trim().length() > 6) {
            super.patientsList.setAll(DatabaseController.getPatientsForMagazine(search, check, begin, end));
            super.patientsList.sorted();
        } else {
            super.patientsList.setAll(DatabaseController.getPatientsForMagazine());
            super.patientsList.sorted();
        }
        updateAddresses();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
