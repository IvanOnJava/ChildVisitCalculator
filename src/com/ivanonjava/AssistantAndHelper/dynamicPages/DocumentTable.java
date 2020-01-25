package com.ivanonjava.AssistantAndHelper.dynamicPages;

import com.ivanonjava.AssistantAndHelper.Constants;
import com.ivanonjava.AssistantAndHelper.UI.controllers.DocumentPageControllers;
import com.ivanonjava.AssistantAndHelper.domains.DatabaseController;
import com.ivanonjava.AssistantAndHelper.domains.LogController;
import com.ivanonjava.AssistantAndHelper.helpers.ActionButtonTableCell;
import com.ivanonjava.AssistantAndHelper.helpers.TranslateWord;
import com.ivanonjava.AssistantAndHelper.pojo.PatientForDocument;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.Arrays;

public abstract class DocumentTable<T extends PatientForDocument> extends TableView<T> implements Initializable {
    ObservableList<T> patientsList = FXCollections.observableArrayList();
    private ObservableList<String> addresses = FXCollections.observableArrayList();

    private TableColumn<T, Integer> t_id = new TableColumn<>(Constants.NAME_TABLE_ID);

    private TableColumn<T, Button> b_delete = new TableColumn<>(Constants.NAME_TABLE_BUTTON);

    private TableColumn<T, String> t_fio = new TableColumn<>(Constants.NAME_TABLE_FULLNAME);

    private TableColumn<T, String> t_birth = new TableColumn<>(Constants.NAME_TABLE_BIRTHDAY);

    private TableColumn<T, String> t_disc = new TableColumn<>(Constants.NAME_TABLE_DISCARDDAY);

    private TableColumn<T, String> t_comment = new TableColumn<>(Constants.NAME_TABLE_COMMENT);

    private TableColumn<T, Boolean> t_present = new TableColumn<>(Constants.NAME_TABLE_PRESENT);

    private TableColumn<T, String> adr = new TableColumn<>(Constants.NAME_TABLE_ADDRESS);
    private TableColumn<T, String> t_street = new TableColumn<>(Constants.NAME_TABLE_STREET);
    private TableColumn<T, String> t_apart = new TableColumn<>(Constants.NAME_TABLE_APARTMENT);

    private TableColumn<T, String> t_phone = new TableColumn<>(Constants.NAME_TABLE_PHONE);

    private TableColumn<T, String> t_one = new TableColumn<>(Constants.NAME_TABLE_ONE_DAY);


    void updateAddresses() {
        LogController.write("DocumentTable.updateAddresses()");
        addresses.setAll(DatabaseController.getAddresses());
    }

    @SafeVarargs
    final <A extends TableColumn<T, String>> void setCell(A... nodes) {
        for (TableColumn<T, String> table :
                nodes) {
            table.setCellFactory(TextFieldTableCell.forTableColumn());
            this.getColumns().add(table);
        }
        LogController.write("DocumentTable.setCell(" + Arrays.toString(nodes) + ")");
    }

    @Override
    public void sort() {
        try {
            String action = TranslateWord.translateTableNameToDatabaseName(getSortOrder().get(0).getText());
            boolean isAskType = getSortOrder().get(0).getSortType() == TableColumn.SortType.ASCENDING;
            if (action != null) {
                DatabaseController.setSortAction(action, isAskType);
            }
        } catch (IndexOutOfBoundsException ignore) {
        }

        super.sort();
    }

    void configDocumentTable() {

        LogController.write("DocumentTable.configDocumentTable()");
        addresses.setAll(DatabaseController.getAddresses());
        setEditable(true);

        t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        b_delete.setCellFactory(ActionButtonTableCell.forTableColumn("Удалить", (T p) -> {
            patientsList.remove(p);
            p.deletePatient();
            return p;
        }));
        b_delete.setCellValueFactory(new PropertyValueFactory<>("button_delete"));
        this.getColumns().add(b_delete);
        this.getColumns().add(t_id);

        t_fio.setCellValueFactory(new PropertyValueFactory<>("FIO"));
        t_fio.setOnEditCommit(this::editColumnName);
        setCell(t_fio);
        t_birth.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        t_birth.getComparator().reversed();
        this.getColumns().add(t_birth);

        t_disc.setCellValueFactory(new PropertyValueFactory<>("discardDay"));
        this.getColumns().add(t_disc);

        t_present.setCellValueFactory(param -> {
            T person = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(person.isIsPresent());
            booleanProp.addListener((observable, oldValue, newValue) -> person.setIsPresent(newValue));
            return booleanProp;
        });
        t_present.setCellFactory(p -> {
            CheckBoxTableCell<T, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        this.getColumns().add(t_present);

        t_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        t_comment.setOnEditCommit(this::editColumnComment);
        setCell(t_comment);

        t_street.setCellValueFactory(new PropertyValueFactory<>("address"));
        t_street.setCellFactory(ComboBoxTableCell.forTableColumn(addresses));
        t_street.setOnEditCommit(this::editColumnAddress);
        adr.getColumns().add(t_street);
        t_apart.setCellValueFactory(new PropertyValueFactory<>("kv"));
        t_apart.setCellFactory(TextFieldTableCell.forTableColumn());
        t_apart.setOnEditCommit(this::editColumnKv);
        adr.getColumns().add(t_apart);
        this.getColumns().add(adr);

        t_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        t_phone.setOnEditCommit(this::editColumnPhone);

        t_one.setCellValueFactory(new PropertyValueFactory<>("oneDay"));
        t_one.setOnEditCommit(this::editColumnOneDay);

        setCell(t_phone, t_one);


    }


    void updated() {
        LogController.write("DocumentTable.updated()");
        DocumentPageControllers.update();
    }

    private void editColumnKv(TableColumn.CellEditEvent<T, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setKv(event.getNewValue());
        LogController.write("DocumentTable.editColumnKv(event = " + event + ")");
        updated();
    }

    private void editColumnOneDay(TableColumn.CellEditEvent<T, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setOneDay(event.getNewValue());
        LogController.write("DocumentTable.editColumnOneDay(event = " + event + ")");
        updated();
    }


    private void editColumnPhone(TableColumn.CellEditEvent<T, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setPhone(event.getNewValue());
        LogController.write("DocumentTable.editColumnPhone(event = " + event + ")");
        updated();
    }

    private void editColumnAddress(TableColumn.CellEditEvent<T, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setAddress(event.getNewValue());
        LogController.write("DocumentTable.editColumnAddress(event = " + event + ")");
        updated();
    }


    private void editColumnName(TableColumn.CellEditEvent<T, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setFIO(event.getNewValue());
        LogController.write("DocumentTable.editColumnName(event = " + event + ")");
        updated();
    }

    private void editColumnComment(TableColumn.CellEditEvent<T, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setComment(event.getNewValue());
        LogController.write("DocumentTable.editColumnComment(event = " + event + ")");
        updated();
    }

    public abstract void updateTable();

    abstract void configTable();

    public abstract void saveDocument(String begin, String end);
}
