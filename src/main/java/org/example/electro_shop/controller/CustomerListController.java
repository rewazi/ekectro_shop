package org.example.electro_shop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.electro_shop.model.entity.Customer;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class CustomerListController implements Initializable {

    private final CustomerService customerService;
    private final FormService formService;

    @FXML
    private TableView<Customer> tvCustomerList;
    @FXML
    private TableColumn<Customer, String> tcId;
    @FXML
    private TableColumn<Customer, String> tcUsername;
    @FXML
    private TableColumn<Customer, String> tcFirstname;
    @FXML
    private TableColumn<Customer, String> tcLastname;
    @FXML
    private TableColumn<Customer, String> tcBalance;

    @FXML
    private Button editCustomerButton;
    @FXML
    private Button deleteCustomerButton;

    public CustomerListController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Customer> customers = customerService.getAllCustomers();
        tvCustomerList.setItems(FXCollections.observableArrayList(customers));


        tcId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcUsername.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUsername()));
        tcFirstname.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstname()));
        tcLastname.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLastname()));
        tcBalance.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getBalance())));


        tvCustomerList.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            boolean isAdmin = newSel != null && "admin".equalsIgnoreCase(newSel.getUsername());
            deleteCustomerButton.setDisable(isAdmin);
           ;
        });
    }

    @FXML
    private void editCustomer() {
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            return;
        }

        Customer selectedCustomer = tvCustomerList.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            formService.loadEditCustomerForm(selectedCustomer);
        } else {
            showErrorAlert("Пожалуйста, выберите покупателя для редактирования.");
        }
    }

    @FXML
    private void deleteCustomer() {
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            return;
        }
        Customer selectedCustomer = tvCustomerList.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            if ("admin".equalsIgnoreCase(selectedCustomer.getUsername())) {
                showErrorAlert("Невозможно удалить пользователя с логином 'admin'.");
                return;
            }
            customerService.deleteCustomer(selectedCustomer.getId());
            tvCustomerList.setItems(FXCollections.observableArrayList(customerService.getAllCustomers()));
        } else {
            showErrorAlert("Пожалуйста, выберите покупателя для удаления.");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
