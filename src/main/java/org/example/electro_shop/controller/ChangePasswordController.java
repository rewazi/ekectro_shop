package org.example.electro_shop.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.electro_shop.model.entity.Customer;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.FormService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChangePasswordController {

    private final CustomerService customerService;
    private final FormService formService;

    public ChangePasswordController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    @FXML
    private ComboBox<Customer> cbUsers;
    @FXML
    private TextField tfUserId;
    @FXML
    private PasswordField pfNewPassword;
    @FXML
    private Label lblResult;

    @FXML
    public void initialize() {
        if (CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            List<Customer> allUsers = customerService.getAllCustomers();
            cbUsers.setItems(FXCollections.observableArrayList(allUsers));

            cbUsers.setCellFactory(listView -> new ListCell<>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getUsername());
                    }
                }
            });
            cbUsers.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getUsername());
                    }
                }
            });
            tfUserId.setVisible(false);
        } else {
            cbUsers.setVisible(false);
            if (CustomerService.currentCustomer != null) {
                tfUserId.setText(CustomerService.currentCustomer.getUsername());
            }
            tfUserId.setEditable(false);
            tfUserId.setVisible(true);
        }
    }

    @FXML
    private void handleChangePassword() {
        try {
            Long userId;
            if (CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
                Customer selectedUser = cbUsers.getSelectionModel().getSelectedItem();
                if (selectedUser == null) {
                    lblResult.setText("Выберите пользователя для смены пароля.");
                    return;
                }
                userId = selectedUser.getId();
            } else {
                // Для обычного пользователя берем id из текущего пользователя
                userId = CustomerService.currentCustomer.getId();
            }
            String newPassword = pfNewPassword.getText().trim();
            if (newPassword.isEmpty()) {
                lblResult.setText("Введите новый пароль.");
                return;
            }
            // Вызываем метод смены пароля в CustomerService
            Customer updatedUser = customerService.changePassword(userId, newPassword);
            lblResult.setText("Пароль успешно изменён для пользователя: " + updatedUser.getUsername());
        } catch (SecurityException se) {
            lblResult.setText(se.getMessage());
        } catch (Exception e) {
            lblResult.setText("Ошибка: " + e.getMessage());
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
