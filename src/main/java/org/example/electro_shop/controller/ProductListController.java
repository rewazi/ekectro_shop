package org.example.electro_shop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.electro_shop.model.entity.Product;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.ProductService;
import org.example.electro_shop.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ProductListController implements Initializable {

    private final ProductService productService;
    private final FormService formService;

    @FXML
    private TableView<Product> tvEquipment;

    @FXML
    private TableColumn<Product, String> tcId;
    @FXML
    private TableColumn<Product, String> tcName;
    @FXML
    private TableColumn<Product, String> tcPrice;
    @FXML
    private TableColumn<Product, String> tcQuantity;
    @FXML
    private TableColumn<Product, String> tcStock;

    // Кнопки редактирования и удаления
    @FXML
    private Button editEquipmentButton;
    @FXML
    private Button deleteEquipmentButton;

    public ProductListController(ProductService productService, FormService formService) {
        this.productService = productService;
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tcId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        tcPrice.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        tcQuantity.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
        tcStock.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));

        List<Product> productList = productService.getAllEquipment();
        tvEquipment.setItems(FXCollections.observableArrayList(productList));


        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            editEquipmentButton.setVisible(false);
            deleteEquipmentButton.setVisible(false);
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    @FXML
    private void editSelectedEquipment() {
        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {

            return;
        }
        Product selected = tvEquipment.getSelectionModel().getSelectedItem();
        if (selected != null) {
            formService.loadEditEquipmentForm(selected);
        } else {
            System.out.println("Продукт не выбран!");
        }
    }

    private void showAccessDeniedAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

    }

    @FXML
    private void deleteSelectedEquipment() {
        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {

            return;
        }
        Product selected = tvEquipment.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productService.delete(selected.getId());
            tvEquipment.setItems(FXCollections.observableArrayList(productService.getAllEquipment()));
        } else {
            showAccessDeniedAlert("Продукт не выбран!");
        }
    }
}
