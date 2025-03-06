package com.example.demo.controller;

import com.example.demo.model.entity.Brand;
import com.example.demo.service.BrandService;
import com.example.demo.service.FormService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class BrandFormController implements Initializable {

    private final FormService formService;
    private final BrandService brandService;

    @FXML
    private TextField tfBrandName;  // соответствует fx:id="tfBrandName" в FXML

    public BrandFormController(FormService formService, BrandService brandService) {
        this.formService = formService;
        this.brandService = brandService;
    }

    @FXML
    private void create() throws IOException {
        Brand brand = new Brand();
        // Используем tfBrandName вместо tfName
        brand.setName(tfBrandName.getText());
        brandService.add(brand);
        formService.loadMainForm();
    }

    @FXML
    private void goToMainForm() throws IOException {
        formService.loadMainForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Дополнительная инициализация, если требуется
    }
}
