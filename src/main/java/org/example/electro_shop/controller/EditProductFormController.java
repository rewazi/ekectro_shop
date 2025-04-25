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
import java.util.Set;
import java.util.HashSet;

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

        // Проверка прав пользователя
        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR) &&
                !CustomerService.currentUserHasRole(CustomerService.ROLES.MANAGER)) {
            return;
        }

        // Обновляем все поля
        editProduct.setName(tfName.getText());

        // Устанавливаем цену
        try {
            double price = Double.parseDouble(tfPrice.getText());
            editProduct.setPrice(price);
        } catch (NumberFormatException e) {
            showError("Некорректное значение цены");
            return;
        }

        // Устанавливаем количество
        try {
            int quantity = Integer.parseInt(tfQuantity.getText());
            if (quantity < 0) {
                showError("Количество не может быть меньше 0");
                return;
            }
            editProduct.setQuantity(quantity);
        } catch (NumberFormatException e) {
            showError("Некорректное значение количества");
            return;
        }

        // Устанавливаем количество на складе
        try {
            int stock = Integer.parseInt(tfStock.getText());
            if (stock < 0) {
                showError("Количество на складе не может быть меньше 0");
                return;
            }
            editProduct.setStock(stock);
        } catch (NumberFormatException e) {
            showError("Некорректное значение поля 'В наличии'");
            return;
        }

        // Обновляем поставщиков
        Set<Supplier> selectedSuppliers = new HashSet<>(lvSuppliers.getSelectionModel().getSelectedItems());
        editProduct.setSuppliers(selectedSuppliers);

        // Сохраняем изменения в БД
        productService.update(editProduct);

        // Переходим на главную страницу
        formService.loadMainForm();
    }

    @FXML
    private void goToMainForm() throws IOException {
        formService.loadMainForm();
    }

    // Инициализация данных в форме
    public void setEditEquipment(Product editProduct) {
        this.editProduct = editProduct;
        tfId.setText(editProduct.getId().toString());
        tfName.setText(editProduct.getName());
        tfPrice.setText(String.valueOf(editProduct.getPrice()));
        tfQuantity.setText(String.valueOf(editProduct.getQuantity()));
        tfStock.setText(String.valueOf(editProduct.getStock()));

        // Устанавливаем список поставщиков в ListView
        lvSuppliers.getSelectionModel().clearSelection();
        lvSuppliers.getSelectionModel().selectAll();  // Если нужно выделить всех поставщиков
    }

    // Инициализация поставщиков в ListView
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

    // Вспомогательный метод для отображения ошибки
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
