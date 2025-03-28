package com.example.demo.service;

import com.example.demo.ElectronicsStoreApplication;
import com.example.demo.controller.CustomerFormController;
import com.example.demo.controller.EditProductFormController;
import com.example.demo.model.entity.Product;
import com.example.demo.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FormService {

    private final SpringFXMLLoader springFXMLLoader;

    public FormService(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    public void loadLoginForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/user/loginForm.fxml");
        Parent root = loadRoot(fxmlLoader);
        setScene(root, "Electronics Store - User Login");
    }

    public void loadMainForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/main/mainForm.fxml");
        Parent root = loadRoot(fxmlLoader);
        setScene(root, "Electronics Store - Main");
    }

    public void loadProductTableForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/main/mainForm.fxml");
        Parent root = loadRoot(fxmlLoader);
        setScene(root, "Electronics Store - Products Table");
    }

    public void loadEditProductForm(Product product) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/product/editProductForm.fxml");
        Parent root = loadRoot(fxmlLoader);
        EditProductFormController controller = fxmlLoader.getController();
        controller.setEditProduct(product);
        setScene(root, "Electronics Store - Edit Product");
    }

    public void loadNewProductForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/product/newProductForm.fxml");
        Parent root = loadRoot(fxmlLoader);
        setScene(root, "Electronics Store - Add New Product");
    }

    public void loadRegistrationForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/registrationForm.fxml");
        Parent root = loadRoot(fxmlLoader);
        setScene(root, "Electronics Store - Registration");
    }

    public void loadBrandForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/brand/brandForm.fxml");
        Parent root = loadRoot(fxmlLoader);
        setScene(root, "Electronics Store - Add Brand");
    }

    public Parent loadMenuForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/menu/menuForm.fxml");
        return loadRoot(fxmlLoader);
    }

    public void loadCustomerForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/customerForm.fxml");
        Parent root = loadRoot(fxmlLoader);
        setScene(root, "Electronics Store - Add Customer");
    }

    public void loadEditCustomerForm(com.example.demo.model.entity.StoreUser customer) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/customerForm.fxml");
        Parent root = loadRoot(fxmlLoader);
        CustomerFormController controller = fxmlLoader.getController();
        controller.setEditingCustomer(customer);
        setScene(root, "Electronics Store - Edit Customer");
    }

    // Новый метод для загрузки таблицы покупателей
    public void loadCustomerTableForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/customerTable.fxml");
        Parent root = loadRoot(fxmlLoader);
        setScene(root, "Electronics Store - Customer Table");
    }

    private Parent loadRoot(FXMLLoader fxmlLoader) {
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Cannot load FXML", e);
        }
    }

    private void setScene(Parent root, String title) {
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle(title);
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    private Stage getPrimaryStage() {
        return ElectronicsStoreApplication.primaryStage;
    }
}
