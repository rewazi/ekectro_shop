package com.example.demo.controller;

import com.example.demo.service.FormService;
import com.example.demo.service.StoreUserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class LoginFormController {

    private FormService formService;
    private StoreUserService storeUserService;

    @FXML
    private Label lbInfo;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;

    public LoginFormController(FormService formService, StoreUserService storeUserService) {
        this.formService = formService;
        this.storeUserService = storeUserService;
    }

    @FXML
    private void login(){
        if (storeUserService.authentication(tfUsername.getText(), pfPassword.getText())) {
            if (StoreUserService.currentUser.getRoles().contains(StoreUserService.ROLES.ADMINISTRATOR.toString())) {
                formService.loadMainForm(); // Главная страница для админа
            } else {
                formService.loadProductTableForm(); // Страница с продуктами для пользователя
            }
        } else {
            lbInfo.setText("Invalid username or password");
        }
    }

    @FXML
    private void showRegistrationForm(){
        formService.loadRegistrationForm();
    }
}
