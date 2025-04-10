package org.example.electro_shop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.electro_shop.model.entity.Product;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.ProductService;
import org.example.electro_shop.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class MainFormController implements Initializable {

    private final FormService formService;
    private final ProductService productService;

    @FXML
    private VBox vbMainFormRoot;

    @FXML
    private TableView<Product> tvEquipmentList;

    @FXML
    private TableColumn<Product, String> tcId;

    @FXML
    private TableColumn<Product, String> tcName;

    @FXML
    private TableColumn<Product, String> tcSuppliers;

    @FXML
    private TableColumn<Product, String> tcPrice;

    @FXML
    private TableColumn<Product, String> tcQuantity;

    @FXML
    private TableColumn<Product, String> tcStock;

    @FXML
    private HBox hbEditEquipment;

    public MainFormController(FormService formService, ProductService productService) {
        this.formService = formService;
        this.productService = productService;
    }

    @FXML
    private void showEditEquipmentForm() {
        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка доступа");
            alert.setHeaderText("Редактирование запрещено");
            alert.setContentText("У вас нет прав для редактирования оборудования.");
            alert.showAndWait();
            return;
        }
        Product selectedProduct = tvEquipmentList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            formService.loadEditEquipmentForm(selectedProduct);
        } else {
            System.out.println("Оборудование не выбрано!");
        }
    }

    @FXML
    private void showSelectedEquipmentForm() {
        Product selectedProduct = tvEquipmentList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            formService.loadSelectedEquipmentForm(selectedProduct);
        } else {
            System.out.println("Оборудование не выбрано!");
        }
    }

    @FXML
    private void deleteSelectedEquipment() {
        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка доступа");
            alert.setHeaderText("Удаление запрещено");
            alert.setContentText("У вас нет прав для удаления оборудования.");
            alert.showAndWait();
            return;
        }
        Product selectedProduct = tvEquipmentList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productService.delete(selectedProduct.getId());
            tvEquipmentList.setItems(productService.getAllEquipment());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Добавляем меню в начало главного окна
        vbMainFormRoot.getChildren().addFirst(formService.loadMenuForm());

        // Заполняем таблицу оборудованием
        tvEquipmentList.setItems(productService.getAllEquipment());

        // Настраиваем колонки таблицы
        tcId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        tcSuppliers.setCellValueFactory(cellData -> {
            Product product = cellData.getValue();
            if (product.getSuppliers() == null || product.getSuppliers().isEmpty()) {
                return new SimpleStringProperty("");
            }
            String suppliers = product.getSuppliers().stream()
                    .map(supplier -> supplier.getName())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(suppliers);
        });
        tcPrice.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        tcQuantity.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
        tcStock.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));

        // При выборе строки отображаем панель редактирования
        tvEquipmentList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                hbEditEquipment.setVisible(newValue != null);
            }
        });

        // Обработчик двойного клика: при двойном клике по строке открывается подробная информация
        tvEquipmentList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Product selectedProduct = tvEquipmentList.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    formService.loadSelectedEquipmentForm(selectedProduct);
                }
            }
        });
    }
}
