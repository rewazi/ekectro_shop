package org.example.electro_shop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.electro_shop.model.entity.Supplier;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.FormService;
import org.example.electro_shop.service.SupplierService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SupplierListController implements Initializable {

    private final SupplierService supplierService;
    private final FormService formService;

    @FXML
    private TableView<Supplier> tvSupplierList;

    @FXML
    private TableColumn<Supplier, String> tcId;

    @FXML
    private TableColumn<Supplier, String> tcName;

    @FXML
    private TableColumn<Supplier, String> tcContact;

    @FXML
    private Button deleteSupplierButton;

    public SupplierListController(SupplierService supplierService, FormService formService) {
        this.supplierService = supplierService;
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvSupplierList.setItems(FXCollections.observableArrayList(supplierService.getAllSuppliers()));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tcContact.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact()));
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    @FXML
    private void editSelectedSupplier() {
        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            showAccessDeniedAlert("У вас нет прав для редактирования поставщика.");
            return;
        }
        Supplier selectedSupplier = tvSupplierList.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            formService.loadEditSupplierForm(selectedSupplier);
        } else {
            System.out.println("Поставщик не выбран!");
        }
    }

    private void showAccessDeniedAlert(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Ошибка доступа");
        alert.setHeaderText("Редактирование запрещено");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void deleteSelectedSupplier() {
        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            showAccessDeniedAlert("Нельзя вам это делать!");
            return;
        }
        Supplier selectedSupplier = tvSupplierList.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            supplierService.deleteSupplier(selectedSupplier.getId());
            tvSupplierList.setItems(FXCollections.observableArrayList(supplierService.getAllSuppliers()));
        } else {
            showAccessDeniedAlert("Поставщик не выбран!");
        }
    }

}
