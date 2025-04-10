package org.example.electro_shop.controller;

import javafx.scene.control.*;
import org.example.electro_shop.model.entity.Product;
import org.example.electro_shop.model.entity.Supplier;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.SupplierService;
import org.example.electro_shop.service.ProductService;
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
public class EditProductFormController implements Initializable {
    private final FormService formService;
    private final ProductService productService;
    private final SupplierService supplierService;
    private Product editProduct;

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

    public EditProductFormController(FormService formService, ProductService productService, SupplierService supplierService) {
        this.formService = formService;
        this.productService = productService;
        this.supplierService = supplierService;
    }

    @FXML
    private void goEdit() throws IOException {

        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR) &&
                !CustomerService.currentUserHasRole(CustomerService.ROLES.MANAGER)) {

            showAccessDeniedAlert("У вас нет прав на редактирование продуктов.");
            return;
        }


        editProduct.setName(tfName.getText());
        productService.update(editProduct);
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

    public void setEditEquipment(Product editProduct) {
        this.editProduct = editProduct;
        tfId.setText(editProduct.getId().toString());
        tfName.setText(editProduct.getName());
        tfPrice.setText(String.valueOf(editProduct.getPrice()));
        tfQuantity.setText(String.valueOf(editProduct.getQuantity()));
        tfStock.setText(String.valueOf(editProduct.getStock()));
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
