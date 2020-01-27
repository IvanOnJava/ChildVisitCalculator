package com.ivanonjava.ChildVisitCalculator.dynamicPages;

import com.ivanonjava.ChildVisitCalculator.Constants;
import com.ivanonjava.ChildVisitCalculator.domains.DatabaseController;
import com.ivanonjava.ChildVisitCalculator.helpers.ActionButtonTableCell;
import com.ivanonjava.ChildVisitCalculator.pojo.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AddressPage extends Stage {
    private ObservableList<Address> adrList = FXCollections.observableArrayList();
    private TableView<Address> address = new TableView<>();
    private TableColumn<Address, String> t_adr = new TableColumn<>("Адрес");
    private TableColumn<Address, String> t_numb = new TableColumn<>("Номер участка");
    private TableColumn<Address, Button> t_delete = new TableColumn<>();
    private Button addAdr = new Button("Добавить");

    private Stage addAddress() {
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Constants.PATH_ICON_ADDRESS));
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        TextField addr = new TextField();
        addr.setPromptText("Адрес");
        TextField num = new TextField();
        num.setPromptText("номер участка");
        hbox.getChildren().addAll(addr, num);
        vbox.getChildren().add(hbox);
        Button button = new Button("Добавить");
        button.setOnAction(event -> {
            if (addr.getText().trim().length() > 3 && num.getText().trim().length() > 0) {
                if (DatabaseController.addAddress(addr.getText(), num.getText())) {
                    addr.clear();
                    num.clear();
                    update();
                }
            }

        });
        vbox.getChildren().add(button);
        stage.setScene(new Scene(vbox));
        return stage;
    }

    public AddressPage() {
        getIcons().add(new Image(Constants.PATH_ICON_ADDRESS));
        AnchorPane pane = new AnchorPane();
        address.setEditable(true);
        config();
        AnchorPane.setBottomAnchor(address, 10.0);
        AnchorPane.setLeftAnchor(address, 10.0);
        AnchorPane.setRightAnchor(address, 10.0);
        AnchorPane.setTopAnchor(address, 50.0);

        AnchorPane.setTopAnchor(addAdr, 10.0);
        AnchorPane.setRightAnchor(addAdr, 10.0);
        pane.getChildren().addAll(addAdr, address);
        update();
        Scene scene = new Scene(pane);
        setMinHeight(500);
        setMinWidth(400);

        setScene(scene);
    }

    private void config() {
        t_adr.setCellValueFactory(new PropertyValueFactory<>("address"));
        t_adr.setCellFactory(TextFieldTableCell.forTableColumn());
        t_adr.setOnEditCommit(this::editColumnAddress);
        address.getColumns().add(t_adr);

        t_numb.setCellValueFactory(new PropertyValueFactory<>("number"));
        t_numb.setCellFactory(TextFieldTableCell.forTableColumn());
        t_numb.setOnEditCommit(this::editColumnNumber);
        address.getColumns().add(t_numb);
        addAdr.setOnAction(event -> addAddress().show());
        address.getItems().clear();
        address.setItems(adrList);

        t_delete.setCellFactory(ActionButtonTableCell.forTableColumn("Удалить", (Address p) -> {
            adrList.remove(p);
            p.deleteAddress();
            return p;
        }));
        t_delete.setCellValueFactory(new PropertyValueFactory<>("button_delete"));
        address.getColumns().add(t_delete);
    }

    private void update() {
        adrList.clear();
        adrList.addAll(DatabaseController.getAllAddress());
    }

    private void editColumnNumber(TableColumn.CellEditEvent<Address, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setNumber(event.getNewValue());
        update();
    }


    private void editColumnAddress(TableColumn.CellEditEvent<Address, String> event) {
        event.getTableView().getItems().get(
                event.getTablePosition().getRow()).setAddress(event.getNewValue());
        update();
    }
}
