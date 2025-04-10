package org.example.electro_shop.controller;

import org.example.electro_shop.model.entity.Supplier;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.SupplierService;
import org.example.electro_shop.service.FormService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SupplierFormController implements Initializable {

    private final FormService formService;
    private final SupplierService supplierService;
    private final CustomerService customerService;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfContact;

    public SupplierFormController(FormService formService, SupplierService supplierService, CustomerService customerService) {
        this.formService = formService;
        this.supplierService = supplierService;
        this.customerService = customerService;
    }

    @FXML
    private void create() throws IOException {

        if (tfName.getText() == null || tfName.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Пустое поле");
            alert.setContentText("Название поставщика не может быть пустым.");
            alert.showAndWait();
            return;
        }

        Supplier supplier = new Supplier();
        supplier.setName(tfName.getText());
        supplier.setContact(tfContact.getText());
        supplierService.add(supplier);


        tfName.clear();
        tfContact.clear();

        formService.loadMainForm();
    }

    @FXML
    private void goToMainForm() throws IOException {
        formService.loadMainForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (!CustomerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);



            formService.loadMainForm();
        }
    }
}
