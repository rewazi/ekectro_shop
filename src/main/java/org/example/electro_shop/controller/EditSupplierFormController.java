package org.example.electro_shop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.electro_shop.model.entity.Supplier;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.FormService;
import org.example.electro_shop.service.SupplierService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EditSupplierFormController {

    private final SupplierService supplierService;
    private final FormService formService;
    private final CustomerService customerService;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfContact;

    private Supplier editSupplier;

    public EditSupplierFormController(SupplierService supplierService, FormService formService, CustomerService customerService) {
        this.supplierService = supplierService;
        this.formService = formService;
        this.customerService = customerService;
    }


    public void setEditSupplier(Supplier supplier) {
        this.editSupplier = supplier;
        if (supplier != null) {
            tfId.setText(String.valueOf(supplier.getId()));
            tfName.setText(supplier.getName());
            tfContact.setText(supplier.getContact());
        }
    }

    @FXML
    private void saveSupplier() throws IOException {

        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR) &&
                !customerService.currentUserHasRole(CustomerService.ROLES.MANAGER)) {

            return;
        }


        editSupplier.setName(tfName.getText());
        editSupplier.setContact(tfContact.getText());
        supplierService.updateSupplier(editSupplier);
        formService.loadSupplierListForm();
    }



    @FXML
    private void cancelEdit() throws IOException {
        formService.loadSupplierListForm();
    }
}
