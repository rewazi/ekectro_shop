package org.example.electro_shop.controller;

import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class LoginFormController {
    private final FormService formService;
    private final CustomerService customerService;

    @FXML
    private Label lbInfo;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    public LoginFormController(FormService formService, CustomerService customerService) {
        this.formService = formService;
        this.customerService = customerService;
    }

    @FXML
    private void login() {
        if(customerService.authenticate(tfUsername.getText(), pfPassword.getText())){
            formService.loadMainForm();
        } else {
            lbInfo.setText("Неверное имя пользователя или пароль");
        }
    }

    @FXML
    private void showRegistrationForm() {
        formService.loadRegistrationForm();
    }
}
