package com.example.demo.controller;

import com.example.demo.service.FormService;
import com.example.demo.service.StoreUserService;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuFormController {

    private final FormService formService;

    public MenuFormController(FormService formService) {
        this.formService = formService;
    }

    @FXML
    private Menu productMenu;

    @FXML
    private Menu customerMenu;

    @FXML
    private Menu userMenu;  // Этот элемент будет видим для всех ролей

    @FXML
    private MenuItem showProductForm;

    @FXML
    private MenuItem showBrandForm;

    @FXML
    private MenuItem showCustomerForm;

    @FXML
    private MenuItem showCustomerTable;

    @FXML
    private MenuItem showLoginForm;

    @FXML
    private MenuItem logoutMenuItem;

    /**
     * Инициализация меню на основе роли текущего пользователя.
     * Все пользователи видят меню "Пользователь".
     * Продукты и покупатели скрыты для ролей "CUSTOMER".
     */
    @FXML
    private void initialize() {
        // Проверяем роль текущего пользователя
        if (StoreUserService.currentUser != null) {
            // Меню "Пользователь" всегда видимо
            userMenu.setVisible(true);

            // Если текущий пользователь - администратор или менеджер
            if (StoreUserService.currentUser.getRoles().contains(StoreUserService.ROLES.ADMINISTRATOR.toString()) ||
                    StoreUserService.currentUser.getRoles().contains(StoreUserService.ROLES.MANAGER.toString())) {
                // Показать все меню для администраторов и менеджеров
                productMenu.setVisible(true);
                customerMenu.setVisible(true);
            } else {
                // Для обычного пользователя (CUSTOMER), скрываем меню продуктов и покупателей
                productMenu.setVisible(false);  // Скрыть меню Продукты
                customerMenu.setVisible(false);  // Скрыть меню Покупатели
            }
        }
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
        StoreUserService.currentUser = null;
        formService.loadLoginForm();
    }
}
