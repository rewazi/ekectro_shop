package org.example.electro_shop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.electro_shop.model.entity.Customer;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EditCustomerFormController {

    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField balanceField;
    @FXML
    private ComboBox<String> roleComboBox;

    private final CustomerService customerService;
    private final FormService formService;
    private Customer customer;


    @Autowired
    public EditCustomerFormController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;

        // Убираем проверку роли, делаем кнопку доступной для всех
        firstnameField.setText(customer.getFirstname());
        lastnameField.setText(customer.getLastname());
        usernameField.setText(customer.getUsername());
        balanceField.setText(String.valueOf(customer.getBalance()));

        // Устанавливаем роль (по умолчанию "CUSTOMER" или текущую роль пользователя)
        roleComboBox.setValue(customer.getRoles().isEmpty() ? "CUSTOMER" : customer.getRoles().iterator().next());

        // Блокируем поле username только для редактирования пользователя "admin" или текущего пользователя
        if (customer.getUsername().equals("admin") || customer.getUsername().equals(customerService.currentUser().getUsername())) {
            usernameField.setDisable(true);  // Блокируем поле для редактирования имени пользователя
        }

        // Блокируем изменение роли для текущего пользователя или пользователя с логином "admin"
        if (customer.getUsername().equals(customerService.currentUser().getUsername()) || customer.getUsername().equals("admin")) {
            roleComboBox.setDisable(true);  // Блокируем поле для редактирования роли
        }
    }

    @FXML
    private void saveCustomer() {
        if (customer != null) {

            customer.setFirstname(firstnameField.getText());
            customer.setLastname(lastnameField.getText());

            // Блокируем изменение имени пользователя для "admin" и текущего пользователя
            if (!customer.getUsername().equals("admin") && !customer.getUsername().equals(customerService.currentUser().getUsername())) {
                customer.setUsername(usernameField.getText());
                String selectedRole = roleComboBox.getValue();
                customer.getRoles().clear();
                customer.getRoles().add(selectedRole);
            }

            // Обработка поля баланса
            try {
                double balance = Double.parseDouble(balanceField.getText());
                customer.setBalance(balance);
            } catch (NumberFormatException e) {
                showError("Баланс должен быть числом!");
                return;
            }

            // Сохранение изменений
            try {
                customerService.update(customer);
                showSuccess("Покупатель успешно обновлен!");
                formService.loadCustomerListForm();
            } catch (Exception e) {
                showError("Ошибка обновления: " + e.getMessage());
            }
        }
    }

    @FXML
    private void cancelEdit() {
        formService.loadCustomerListForm();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Что-то пошло не так");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText("Операция выполнена успешно");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
