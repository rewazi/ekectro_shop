package com.example.demo.controller;

import com.example.demo.model.entity.Brand;
import com.example.demo.model.entity.Product;
import com.example.demo.service.BrandService;
import com.example.demo.service.ProductService;
import com.example.demo.service.FormService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class EditProductFormController implements Initializable {

    private FormService formService;
    private ProductService productService;
    private BrandService brandService;
    private Product editProduct;

    @FXML
    private TextField tfId;
    @FXML
    private TextField tfName;
    @FXML
    private ListView<Brand> lvBrands;
    @FXML
    private TextField tfReleaseYear;
    @FXML
    private TextField tfQuantity;
    @FXML
    private TextField tfCount;

    public EditProductFormController(FormService formService, ProductService productService, BrandService brandService) {
        this.formService = formService;
        this.productService = productService;
        this.brandService = brandService;
    }

    @FXML
    private void goEdit() throws IOException {
        editProduct.setName(tfName.getText());
        // Выбираем бренд
        editProduct.setBrand((com.example.demo.model.entity.Brand) lvBrands.getSelectionModel().getSelectedItem());
        editProduct.setReleaseYear(Integer.parseInt(tfReleaseYear.getText()));
        // Общее количество продукта
        editProduct.setQuantity(Integer.parseInt(tfQuantity.getText()));
        // Количество в наличии задаётся отдельно
        editProduct.setCount(Integer.parseInt(tfCount.getText()));
        productService.create(editProduct);
        formService.loadMainForm();
    }

    @FXML
    private void goToMainForm() throws IOException {
        formService.loadMainForm();
    }

    public void setEditProduct(Product editProduct) {
        this.editProduct = editProduct;
        tfId.setText(editProduct.getId().toString());
        tfName.setText(editProduct.getName());
        tfReleaseYear.setText(String.valueOf(editProduct.getReleaseYear()));
        tfQuantity.setText(String.valueOf(editProduct.getQuantity()));
        tfCount.setText(String.valueOf(editProduct.getCount()));
        lvBrands.getSelectionModel().select(editProduct.getBrand());
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
                    setText("ID: " + brand.getId() + " - " + brand.getName());
                }
            }
        });
    }
}
