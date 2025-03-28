package com.example.demo.controller;

import com.example.demo.model.entity.Brand;
import com.example.demo.model.entity.Product;
import com.example.demo.service.BrandService;
import com.example.demo.service.FormService;
import com.example.demo.service.ProductService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class NewProductFormController implements Initializable {

    private final FormService formService;
    private final ProductService productService;
    private final BrandService brandService;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfReleaseYear;

    @FXML
    private TextField tfQuantity;

    @FXML
    private TextField tfCount; // Обязательно объявляем это поле

    @FXML
    private ListView<Brand> lvBrands;

    public NewProductFormController(FormService formService, ProductService productService, BrandService brandService) {
        this.formService = formService;
        this.productService = productService;
        this.brandService = brandService;
    }

    @FXML
    private void create() {
        Product product = new Product();
        product.setName(tfName.getText());
        product.setBrand(lvBrands.getSelectionModel().getSelectedItem());
        product.setReleaseYear(Integer.parseInt(tfReleaseYear.getText()));
        product.setQuantity(Integer.parseInt(tfQuantity.getText()));
        product.setCount(Integer.parseInt(tfCount.getText())); // Используем tfCount для "в наличии"
        productService.create(product);
        formService.loadMainForm();
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvBrands.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        List<Brand> brands = brandService.getListBrands();
        lvBrands.getItems().setAll(FXCollections.observableArrayList(brands));
        lvBrands.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Brand brand, boolean empty) {
                super.updateItem(brand, empty);
                if (empty || brand == null) {
                    setText(null);
                } else {
                    setText(brand.getId() + ". " + brand.getName());
                }
            }
        });
    }
}
