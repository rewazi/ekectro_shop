package com.example.demo.controller;

import com.example.demo.service.FormService;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;

@Component
public class MenuFormController {

    private final FormService formService;

    public MenuFormController(FormService formService) {
        this.formService = formService;
    }

    @FXML
    private void showProductForm() {
        formService.loadNewProductForm();
    }

    @FXML
    private void showBrandForm() {
        formService.loadBrandForm();
    }

    @FXML
    private void showCustomerForm() {
        formService.loadCustomerForm();
    }

    @FXML
    private void showCustomerTable() {
        formService.loadCustomerTableForm();
    }

    @FXML
    private void showLoginForm() {
        formService.loadLoginForm();
    }

    @FXML
    private void logout() {
        // Например, сброс текущего пользователя
        // StoreUserService.currentUser = null;
        formService.loadLoginForm();
    }
}
