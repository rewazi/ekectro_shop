package org.example.electro_shop.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import org.example.electro_shop.model.entity.Customer;
import org.example.electro_shop.model.entity.Product;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.ProductService;
import org.example.electro_shop.service.FormService;
import org.example.electro_shop.service.PurchaseService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class PurchaseFormController implements Initializable {

    private final PurchaseService purchaseService;
    private final FormService formService;
    private final CustomerService customerService;
    private final ProductService productService;

    @FXML
    private ComboBox<Product> cbEquipment;
    @FXML
    private TextField tfQuantity;
    @FXML
    private Label lblPurchaseResult;

    public PurchaseFormController(PurchaseService purchaseService,
                                  FormService formService,
                                  CustomerService customerService,
                                  ProductService productService) {
        this.purchaseService = purchaseService;
        this.formService = formService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        cbEquipment.setItems(FXCollections.observableArrayList(productService.getAllEquipment()));


        cbEquipment.setCellFactory(listView -> new ListCell<Product>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " - " + item.getPrice() + " евро.");
                }
            }
        });
        cbEquipment.setButtonCell(new ListCell<Product>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " - " + item.getPrice() + " евро.");
                }
            }
        });
    }

    @FXML
    private void handlePurchase() {
        try {

            Customer customer = customerService.currentCustomer;
            Product product = cbEquipment.getSelectionModel().getSelectedItem();
            int quantity = Integer.parseInt(tfQuantity.getText().trim());


            if (product == null) {
                lblPurchaseResult.setText("Выберите товар!");
                return;
            }


            if (product.getStock() < quantity) {
                lblPurchaseResult.setText("Недостаточно товара в наличии!");
                return;
            }


            String result = purchaseService.buyEquipment(customer.getId(), product.getId(), quantity);
            lblPurchaseResult.setText(result);
        } catch (NumberFormatException e) {
            lblPurchaseResult.setText("Неверный формат количества!");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
