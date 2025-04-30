package org.example.electro_shop.service;

import org.example.electro_shop.controller.EditCustomerFormController;
import org.example.electro_shop.controller.EditProductFormController;
import org.example.electro_shop.controller.EditSupplierFormController;
import org.example.electro_shop.controller.SelectedProductFormController;
import org.example.electro_shop.model.entity.Customer;
import org.example.electro_shop.model.entity.Product;
import org.example.electro_shop.model.entity.Supplier;
import org.example.electro_shop.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FormService {
    private Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;


    @Autowired
    public FormService(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public Stage getPrimaryStage() {
        if (primaryStage == null) {
            throw new IllegalStateException("Primary stage not set in FormService");
        }
        return primaryStage;
    }

    private Parent loadRoot(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadLoginForm() {
        FXMLLoader loader = springFXMLLoader.load("/user/loginForm.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("ElectroStore - Вход");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadRegistrationForm() {
        FXMLLoader loader = springFXMLLoader.load("/user/registrationForm.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Регистрация нового пользователя");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadMainForm() {
        FXMLLoader loader = springFXMLLoader.load("/main/mainForm.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("ElectroStore - Главная");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public Parent loadMenuForm() {
        FXMLLoader loader = springFXMLLoader.load("/menu/menuForm.fxml");
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadNewEquipmentForm() {
        FXMLLoader loader = springFXMLLoader.load("/product/newEquipmentForm.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового продукта");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadEquipmentListForm() {
        FXMLLoader loader = springFXMLLoader.load("/product/ProductList.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Список продуктов");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadEditEquipmentForm(Product product) {
        FXMLLoader loader = springFXMLLoader.load("/product/editProductForm.fxml");
        try {
            Parent root = loader.load();
            EditProductFormController controller = loader.getController();
            controller.setEditEquipment(product);
            Scene scene = new Scene(root);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setTitle("Редактирование продукта");
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSelectedEquipmentForm(Product product) {
        FXMLLoader loader = springFXMLLoader.load("/product/selectedProductForm.fxml");
        try {
            Parent root = loader.load();
            SelectedProductFormController controller = loader.getController();
            controller.setEquipment(product);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Информация о продукте");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSupplierForm() {
        FXMLLoader loader = springFXMLLoader.load("/supplier/supplierForm.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового поставщика");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadSupplierListForm() {
        FXMLLoader loader = springFXMLLoader.load("/supplier/supplierList.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Список поставщиков");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadEditSupplierForm(Supplier supplier) {
        FXMLLoader loader = springFXMLLoader.load("/supplier/editSupplierForm.fxml");
        try {
            Parent root = loader.load();
            EditSupplierFormController controller = loader.getController();
            controller.setEditSupplier(supplier);
            Scene scene = new Scene(root);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setTitle("Редактирование поставщика");
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadNewCustomerForm() {
        FXMLLoader loader = springFXMLLoader.load("/customer/newCustomerForm.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового покупателя");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadCustomerListForm() {
        FXMLLoader loader = springFXMLLoader.load("/customer/customerList.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Список покупателей");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadEditCustomerForm(Customer customer) {
        FXMLLoader loader = springFXMLLoader.load("/customer/editCustomerForm.fxml");
        try {
            Parent root = loader.load();
            EditCustomerFormController controller = loader.getController();
            controller.setCustomer(customer);
            Scene scene = new Scene(root);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setTitle("Редактирование покупателя");
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPurchaseForm() {
        FXMLLoader loader = springFXMLLoader.load("/purchase/purchaseForm.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Покупка товара");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadIncomeForm() {
        FXMLLoader loader = springFXMLLoader.load("/purchase/incomeForm.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Доход магазина");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadChangePasswordForm() {
        FXMLLoader loader = springFXMLLoader.load("/user/ChangePasswordForm.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Смена пароля");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadRatingForm() {
        FXMLLoader loader = springFXMLLoader.load("/purchase/ratingForm.fxml");
        Parent root = loadRoot(loader);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Рейтинг товаров");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }
}
