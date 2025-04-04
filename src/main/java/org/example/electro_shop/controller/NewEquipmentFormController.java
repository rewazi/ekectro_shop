package org.example.electro_shop.controller;

import org.example.electro_shop.model.entity.Equipment;
import org.example.electro_shop.model.entity.Supplier;
import org.example.electro_shop.service.EquipmentService;
import org.example.electro_shop.service.FormService;
import org.example.electro_shop.service.SupplierService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class NewEquipmentFormController implements Initializable {

    private final FormService formService;
    private final EquipmentService equipmentService;
    private final SupplierService supplierService;

    @FXML
    private TextField tfName;

    @FXML
    private ListView<Supplier> lvSuppliers;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfQuantity;

    public NewEquipmentFormController(FormService formService, EquipmentService equipmentService, SupplierService supplierService) {
        this.formService = formService;
        this.equipmentService = equipmentService;
        this.supplierService = supplierService;
    }

    @FXML
    private void create() {
        Equipment equipment = new Equipment();
        equipment.setName(tfName.getText());
        equipment.getSuppliers().addAll(lvSuppliers.getSelectionModel().getSelectedItems());

        // Обработка поля "Цена"
        double price;
        try {
            price = Double.parseDouble(tfPrice.getText().trim());
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Неверный формат цены");
            alert.setContentText("Пожалуйста, введите число для цены. Буквы не допускаются!");
            alert.showAndWait();
            return;
        }
        equipment.setPrice(price);

        // Обработка поля "Количество"
        int quantity;
        try {
            quantity = Integer.parseInt(tfQuantity.getText().trim());
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Неверный формат количества");
            alert.setContentText("Пожалуйста, введите число для количества. Буквы не допускаются!");
            alert.showAndWait();
            return;
        }
        equipment.setQuantity(quantity);
        equipment.setStock(quantity);

        equipmentService.create(equipment);
        formService.loadMainForm();
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvSuppliers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        lvSuppliers.setItems(FXCollections.observableArrayList(suppliers));
        lvSuppliers.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Supplier supplier, boolean empty) {
                super.updateItem(supplier, empty);
                if (empty || supplier == null) {
                    setText(null);
                } else {
                    setText(supplier.getId() + ". " + supplier.getName());
                }
            }
        });
    }
}
