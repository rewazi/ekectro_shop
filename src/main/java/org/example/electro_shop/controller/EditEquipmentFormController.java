package org.example.electro_shop.controller;

import javafx.scene.control.*;
import org.example.electro_shop.model.entity.Equipment;
import org.example.electro_shop.model.entity.Supplier;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.SupplierService;
import org.example.electro_shop.service.EquipmentService;
import org.example.electro_shop.service.FormService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class EditEquipmentFormController implements Initializable {
    private final FormService formService;
    private final EquipmentService equipmentService;
    private final SupplierService supplierService;
    private Equipment editEquipment;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private ListView<Supplier> lvSuppliers;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfQuantity;

    @FXML
    private TextField tfStock;

    public EditEquipmentFormController(FormService formService, EquipmentService equipmentService, SupplierService supplierService) {
        this.formService = formService;
        this.equipmentService = equipmentService;
        this.supplierService = supplierService;
    }

    @FXML
    private void goEdit() throws IOException {

        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR) &&
                !CustomerService.currentUserHasRole(CustomerService.ROLES.MANAGER)) {

            showAccessDeniedAlert("У вас нет прав на редактирование оборудования.");
            return;
        }


        editEquipment.setName(tfName.getText());
        equipmentService.update(editEquipment);
        formService.loadMainForm();
    }

    private void showAccessDeniedAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка прав доступа");
        alert.setHeaderText("Доступ запрещён");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goToMainForm() throws IOException {
        formService.loadMainForm();
    }

    public void setEditEquipment(Equipment editEquipment) {
        this.editEquipment = editEquipment;
        tfId.setText(editEquipment.getId().toString());
        tfName.setText(editEquipment.getName());
        tfPrice.setText(String.valueOf(editEquipment.getPrice()));
        tfQuantity.setText(String.valueOf(editEquipment.getQuantity()));
        tfStock.setText(String.valueOf(editEquipment.getStock()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvSuppliers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        lvSuppliers.getItems().setAll(FXCollections.observableArrayList(suppliers));
        lvSuppliers.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Supplier supplier, boolean empty) {
                super.updateItem(supplier, empty);
                if (empty || supplier == null) {
                    setText(null);
                } else {
                    setText("ID: " + supplier.getId() + " - " + supplier.getName());
                }
            }
        });
    }
}
