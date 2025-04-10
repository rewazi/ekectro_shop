package org.example.electro_shop.controller;

import org.example.electro_shop.model.entity.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.FormService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SelectedProductFormController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label suppliersLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label stockLabel;

    @FXML
    private Button buyEquipmentButton;

    private Product selectedProduct;

    private final FormService formService;
    private final CustomerService customerService;

    public SelectedProductFormController(FormService formService, CustomerService customerService) {
        this.formService = formService;
        this.customerService = customerService;
    }

    /**
     * Метод вызывается извне (FormService), чтобы передать выбранное оборудование.
     */
    public void setEquipment(Product product) {
        this.selectedProduct = product;
        if (product != null) {

            nameLabel.setText(product.getName());

            String suppliersStr = product.getSuppliers().stream()
                    .map(supplier -> supplier.getName())
                    .collect(Collectors.joining(", "));
            suppliersLabel.setText(suppliersStr);

            priceLabel.setText(String.valueOf(product.getPrice()));
            quantityLabel.setText(String.valueOf(product.getQuantity()));
            stockLabel.setText(String.valueOf(product.getStock()));
        }


    }


    private void showAccessDeniedAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Доступ запрещён");
        alert.setHeaderText("Недостаточно прав");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
