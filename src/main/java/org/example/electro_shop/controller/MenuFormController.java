package org.example.electro_shop.controller;

import javafx.scene.control.Alert;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.FormService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuFormController implements Initializable {

    private final FormService formService;
    private final CustomerService customerService;

    @FXML
    private Menu menuAdministrator;

    public MenuFormController(FormService formService, CustomerService customerService) {
        this.formService = formService;
        this.customerService = customerService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            menuAdministrator.setVisible(false);
        }
    }

    @FXML
    private void showEquipmentForm() {
        if (!CustomerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Доступ запрещён");
            alert.setHeaderText("Недостаточно прав");
            alert.setContentText("У вас нет прав на добавление оборудования!");
            alert.showAndWait();
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
        if (!CustomerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Доступ запрещён");
            alert.setHeaderText("Недостаточно прав");
            alert.setContentText("У вас нет прав на добавление поставщиков!");
            alert.showAndWait();
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
        if (!CustomerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Доступ запрещён");
            alert.setHeaderText("Недостаточно прав");
            alert.setContentText("У вас нет прав на добавление покупателя!");
            alert.showAndWait();
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
        System.out.println("Вы зашли в аккаунт как Администратор! Кнопки Administrator нету у обычного пользователя.");
    }

    @FXML
    private void showLoginForm() {
        formService.loadLoginForm();
    }

    @FXML
    private void logout() {
        CustomerService.currentCustomer = null;
        formService.loadLoginForm();
    }



    @FXML
    private void showPurchaseForm() {

        formService.loadPurchaseForm();
    }

    @FXML
    private void showIncomeForm() {
        if (!CustomerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Доступ запрещён");
            alert.setHeaderText("Недостаточно прав");
            alert.setContentText("У вас нет прав на просмотр дохода магазина!");
            alert.showAndWait();
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
        if (!CustomerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Доступ запрещён");
            alert.setHeaderText("Недостаточно прав");
            alert.setContentText("У вас нет прав на просмотр рейтинга товаров!");
            alert.showAndWait();
            return;
        }
        formService.loadRatingForm();
    }
}
