package org.example.electro_shop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.electro_shop.model.entity.Customer;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.FormService;
import org.springframework.stereotype.Component;

@Component
public class NewCustomerFormController {

    private final CustomerService customerService;
    private final FormService formService;

    @FXML
    private TextField tfFirstname;
    @FXML
    private TextField tfLastname;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfBalance;

    public NewCustomerFormController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    @FXML
    private void createCustomer() {
        try {
            if (tfFirstname.getText().trim().isEmpty() ||
                    tfLastname.getText().trim().isEmpty() ||
                    tfUsername.getText().trim().isEmpty() ||
                    pfPassword.getText().trim().isEmpty() ||
                    tfBalance.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Пустые поля");
                alert.setContentText("Пожалуйста, заполните все поля!");
                alert.showAndWait();
                return;
            }

            Customer customer = new Customer();
            customer.setFirstname(tfFirstname.getText().trim());
            customer.setLastname(tfLastname.getText().trim());
            customer.setUsername(tfUsername.getText().trim());
            customer.setPassword(pfPassword.getText().trim());

            double balance = Double.parseDouble(tfBalance.getText().trim());
            customer.setBalance(balance);
            customer.getRoles().add(CustomerService.ROLES.CUSTOMER.toString());

            customerService.add(customer);
            formService.loadLoginForm();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка регистрации");
            alert.setHeaderText("Регистрация не удалась");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel() {
        formService.loadMainForm();
    }
}
