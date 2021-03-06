package com.ivanonjava.ChildVisitCalculator.dynamicPages;

import com.ivanonjava.ChildVisitCalculator.UI.controllers.DocumentPageControllers;
import com.ivanonjava.ChildVisitCalculator.domains.DatabaseController;
import com.ivanonjava.ChildVisitCalculator.domains.FileController;
import com.ivanonjava.ChildVisitCalculator.helpers.Constants;
import com.ivanonjava.ChildVisitCalculator.pojo.PatientForPatronage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PatronageTable extends DocumentTable<PatientForPatronage> {
    private TableColumn<PatientForPatronage, String> t_three = new TableColumn<>( Constants.getInstance().NAME_TABLE_THREE_DAY);

    private TableColumn<PatientForPatronage, String> t_twoW = new TableColumn<>( Constants.getInstance().NAME_TABLE_TWO_WEEKS);

    private TableColumn<PatientForPatronage, String> t_threeW = new TableColumn<>( Constants.getInstance().NAME_TABLE_THREE_WEEKS);
    private TableColumn<PatientForPatronage, String> patient_oneMonths = new TableColumn<>( Constants.getInstance().NAME_TABLE_ONE_MONTHS);
    private TableColumn<PatientForPatronage, String> patient_twoMonths = new TableColumn<>( Constants.getInstance().NAME_TABLE_TWO_MONTHS);
    private TableColumn<PatientForPatronage, String> patient_threeMonths = new TableColumn<>( Constants.getInstance().NAME_TABLE_THREE_MONTHS);
    private TableColumn<PatientForPatronage, String> patient_fourMonths = new TableColumn<>( Constants.getInstance().NAME_TABLE_FOUR_MONTHS);
    private TableColumn<PatientForPatronage, String> patient_fiveMonths = new TableColumn<>( Constants.getInstance().NAME_TABLE_FIVE_MONTHS);
    private TableColumn<PatientForPatronage, String> patient_sixMonths = new TableColumn<>( Constants.getInstance().NAME_TABLE_SIX_MONTHS);
    private TableColumn<PatientForPatronage, String> patient_sevenMonths = new TableColumn<>( Constants.getInstance().NAME_TABLE_SEVEN_MONTHS);
    private TableColumn<PatientForPatronage, String> patient_eightMonths = new TableColumn<>( Constants.getInstance().NAME_TABLE_EIGHT_MONTHS);
    private TableColumn<PatientForPatronage, String> patient_nineMonths = new TableColumn<>( Constants.getInstance().NAME_TABLE_NINE_MONTHS);
    private TableColumn<PatientForPatronage, String> patient_tenMonths = new TableColumn<>( Constants.getInstance().NAME_TABLE_TEN_MONTHS);
    private TableColumn<PatientForPatronage, String> patient_elevenMonths = new TableColumn<>( Constants.getInstance().NAME_TABLE_ELEVEN_MONTHS);
    private int number;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public PatronageTable(int number) {
        this.number = number;
        configTable();
    }

    @Override
    void configTable() {
        super.patientsList.setAll(DatabaseController.getPatientsForPatronage(number));
        super.configDocumentTable();
        t_three.setCellValueFactory(new PropertyValueFactory<>("threeDay"));
        t_three.setOnEditCommit(this::editColumnThreeDay);

        t_twoW.setCellValueFactory(new PropertyValueFactory<>("twoWeeks"));
        t_twoW.setOnEditCommit(this::editColumnTwoWeeks);

        t_threeW.setCellValueFactory(new PropertyValueFactory<>("threeWeeks"));
        t_threeW.setOnEditCommit(this::editColumnThreeWeeks);
        setCell(t_three, t_twoW, t_threeW);

        patient_oneMonths.setCellValueFactory(new PropertyValueFactory<>("oneMonth"));
        this.getColumns().add(patient_oneMonths);
        patient_twoMonths.setCellValueFactory(new PropertyValueFactory<>("twoMonth"));
        this.getColumns().add(patient_twoMonths);
        patient_threeMonths.setCellValueFactory(new PropertyValueFactory<>("threeMonth"));
        this.getColumns().add(patient_threeMonths);
        patient_fourMonths.setCellValueFactory(new PropertyValueFactory<>("fourMonth"));
        this.getColumns().add(patient_fourMonths);
        patient_fiveMonths.setCellValueFactory(new PropertyValueFactory<>("fiveMonth"));
        this.getColumns().add(patient_fiveMonths);
        patient_sixMonths.setCellValueFactory(new PropertyValueFactory<>("sixMonth"));
        this.getColumns().add(patient_sixMonths);
        patient_sevenMonths.setCellValueFactory(new PropertyValueFactory<>("sevenMonth"));
        this.getColumns().add(patient_sevenMonths);
        patient_eightMonths.setCellValueFactory(new PropertyValueFactory<>("eightMonth"));
        this.getColumns().add(patient_eightMonths);
        patient_nineMonths.setCellValueFactory(new PropertyValueFactory<>("nineMonth"));
        this.getColumns().add(patient_nineMonths);
        patient_tenMonths.setCellValueFactory(new PropertyValueFactory<>("tenMonth"));
        this.getColumns().add(patient_tenMonths);
        patient_elevenMonths.setCellValueFactory(new PropertyValueFactory<>("elevenMonth"));
        this.getColumns().add(patient_elevenMonths);


        this.setItems(patientsList);
    }

    private void editColumnThreeWeeks(TableColumn.CellEditEvent<PatientForPatronage, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setThreeWeeks(event.getNewValue());

        updated();
    }

    private void editColumnTwoWeeks(TableColumn.CellEditEvent<PatientForPatronage, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setTwoWeeks(event.getNewValue());

        updated();
    }

    private void editColumnThreeDay(TableColumn.CellEditEvent<PatientForPatronage, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setThreeDay(event.getNewValue());

        updated();
    }

    @Override
    public void saveDocument(String begin, String end) {
        FileController.savePatronage(begin, end, getNumber());
    }


    @Override
    public void
    updateTable() {
        if(!DocumentPageControllers.search.equalsIgnoreCase("null") && DocumentPageControllers.beginDate.trim().length() > 6) {
            super.patientsList.setAll(DatabaseController.getPatientsForPatronage(DocumentPageControllers.search, DocumentPageControllers.check, DocumentPageControllers.beginDate, DocumentPageControllers.endDate, number));
        }
        else {
            super.patientsList.setAll(DatabaseController.getPatientsForPatronage(number));
        }
        updateAddresses();
    }

    private int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatronageTable that = (PatronageTable) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(t_three, t_twoW, t_threeW, patient_oneMonths, patient_twoMonths, patient_threeMonths, patient_fourMonths, patient_fiveMonths, patient_sixMonths, patient_sevenMonths, patient_eightMonths, patient_nineMonths, patient_tenMonths, patient_elevenMonths, number);
    }
}
