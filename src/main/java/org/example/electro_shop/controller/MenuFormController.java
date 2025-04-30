package org.example.electro_shop.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuFormController implements Initializable {

    private final FormService formService;
    private final CustomerService customerService;


    @FXML
    private Menu menuProducts;
    @FXML
    private Menu menuPurchase;
    @FXML
    private Menu menuUser;

    @FXML
    private Menu menuSuppliers;
    @FXML
    private Menu menuCustomers;
    @FXML
    private Menu menuAdministrator;

    @FXML
    private MenuItem menuItemAddProduct;
    @FXML
    private MenuItem menuItemIncome;
    @FXML
    private MenuItem menuItemRating;

    @FXML
    private MenuItem menuItemAdminFunction; // Кнопка "Админ. функция"
    @FXML
    private MenuItem menuItemCustomers;    // Кнопка "Покупатели"

    public MenuFormController(FormService formService, CustomerService customerService) {
        this.formService = formService;
        this.customerService = customerService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Если текущий пользователь не администратор (и не менеджер), скрываем ненужные элементы
        if (!customerService.currentUserHasAnyRole(CustomerService.ROLES.ADMINISTRATOR)) {
            // Скрываем элементы для обычных пользователей и менеджеров
            menuSuppliers.setVisible(false);
            menuCustomers.setVisible(false);
            menuAdministrator.setVisible(false);

            menuItemAddProduct.setVisible(false);
            menuItemIncome.setVisible(false);
            menuItemRating.setVisible(false);

            // Скрываем "Админ. функция" и "Покупатели" для менеджеров и пользователей
            menuItemAdminFunction.setVisible(false);
            menuItemCustomers.setVisible(false);
        }

        // Если текущий пользователь — это менеджер, показываем все элементы, кроме "Админ. функция" и "Покупатели"
        if (customerService.currentUserHasRole(CustomerService.ROLES.MANAGER)) {
            menuItemAdminFunction.setVisible(false);
            menuItemCustomers.setVisible(false);
            menuSuppliers.setVisible(true);



            menuItemAddProduct.setVisible(true);
            menuItemIncome.setVisible(true);
            menuItemRating.setVisible(true);
        }
    }

    @FXML
    private void showEquipmentForm() {
        if (!customerService.currentUserHasAnyRole(CustomerService.ROLES.MANAGER, CustomerService.ROLES.ADMINISTRATOR)) {
            return;
        }
        formService.loadNewEquipmentForm();
    }

    @FXML
    private void showEquipmentList() {
        formService.loadEquipmentListForm();
    }

    @FXML
    private void showSupplierForm() {
        if (!customerService.currentUserHasAnyRole(CustomerService.ROLES.MANAGER, CustomerService.ROLES.ADMINISTRATOR)) {
            return;
        }
        formService.loadSupplierForm();
    }

    @FXML
    private void showSupplierList() {
        formService.loadSupplierListForm();
    }

    @FXML
    private void showNewCustomerForm() {
        if (!customerService.currentUserHasAnyRole(CustomerService.ROLES.MANAGER, CustomerService.ROLES.ADMINISTRATOR)) {
            return;
        }
        formService.loadNewCustomerForm();
    }

    @FXML
    private void showCustomerListForm() {
        formService.loadCustomerListForm();
    }

    @FXML
    private void someAdminFunction() {
        // Функционал для администраторов (и менеджеров)
        System.out.println("Вы зашли в аккаунт как администратор или менеджер! Вы видите все пункты меню.");
    }

    @FXML
    private void showLoginForm() {
        formService.loadLoginForm();
    }

    @FXML
    private void logout() {
        customerService.setCurrentUser(null);
        formService.loadLoginForm();
    }

    @FXML
    private void showPurchaseForm() {
        formService.loadPurchaseForm();
    }

    @FXML
    private void showIncomeForm() {
        if (!customerService.currentUserHasAnyRole(CustomerService.ROLES.MANAGER, CustomerService.ROLES.ADMINISTRATOR)) {
            return;
        }
        formService.loadIncomeForm();
    }

    @FXML
    private void showChangePasswordForm() {
        formService.loadChangePasswordForm();
    }

    @FXML
    private void showRatingForm() {
        if (!customerService.currentUserHasAnyRole(CustomerService.ROLES.MANAGER, CustomerService.ROLES.ADMINISTRATOR)) {
            return;
        }
        formService.loadRatingForm();
    }
}
