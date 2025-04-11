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

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfContact;

    private Supplier editSupplier;

    public EditSupplierFormController(SupplierService supplierService, FormService formService) {
        this.supplierService = supplierService;
        this.formService = formService;
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

        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR) &&
                !CustomerService.currentUserHasRole(CustomerService.ROLES.MANAGER)) {

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
