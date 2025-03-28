package com.example.demo.controller;

import com.example.demo.model.entity.StoreUser;
import com.example.demo.service.FormService;
import com.example.demo.service.StoreUserService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class RegistrationFormController {

    private StoreUserService storeUserService;
    private FormService formService;

    @FXML
    private TextField tfLastname;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfFirstname;

    public RegistrationFormController(StoreUserService storeUserService, FormService formService) {
        this.storeUserService = storeUserService;
        this.formService = formService;
    }

    @FXML
    private void registration(){
        try {
            StoreUser newUser = new StoreUser();
            newUser.setFirstname(tfFirstname.getText());
            newUser.setLastname(tfLastname.getText());
            newUser.setUsername(tfUsername.getText());
            newUser.setPassword(pfPassword.getText());
            newUser.getRoles().add(StoreUserService.ROLES.CUSTOMER.toString());
            storeUserService.add(newUser);
            formService.loadLoginForm();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
