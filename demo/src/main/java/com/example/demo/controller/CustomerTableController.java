package com.example.demo.controller;

import com.example.demo.model.entity.StoreUser;
import com.example.demo.service.FormService;
import com.example.demo.service.StoreUserService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class CustomerTableController implements Initializable {

    private final FormService formService;
    private final StoreUserService storeUserService;

    @FXML
    private TableView<StoreUser> tvCustomers;
    @FXML
    private TableColumn<StoreUser, String> tcId;
    @FXML
    private TableColumn<StoreUser, String> tcUsername;
    @FXML
    private TableColumn<StoreUser, String> tcFirstname;
    @FXML
    private TableColumn<StoreUser, String> tcLastname;
    @FXML
    private TableColumn<StoreUser, String> tcPassword;

    public CustomerTableController(FormService formService, StoreUserService storeUserService) {
        this.formService = formService;
        this.storeUserService = storeUserService;
    }

    @FXML
    private void goToCustomerForm() {
        formService.loadCustomerForm();
    }

    @FXML
    private void editCustomer() {
        StoreUser selected = tvCustomers.getSelectionModel().getSelectedItem();
        if (selected != null) {
            formService.loadEditCustomerForm(selected);
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcUsername.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        tcFirstname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstname()));
        tcLastname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastname()));
        tcPassword.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));

        List<StoreUser> customers = storeUserService.getAllCustomers();
        tvCustomers.getItems().setAll(customers);
    }
}
