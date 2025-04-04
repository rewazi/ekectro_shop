package org.example.electro_shop.service;

import org.example.electro_shop.ElectroStoreApplication;
import org.example.electro_shop.controller.EditCustomerFormController;
import org.example.electro_shop.controller.EditEquipmentFormController;
import org.example.electro_shop.controller.EditSupplierFormController;
import org.example.electro_shop.controller.SelectedEquipmentFormController;
import org.example.electro_shop.model.entity.Customer;
import org.example.electro_shop.model.entity.Equipment;
import org.example.electro_shop.model.entity.Supplier;
import org.example.electro_shop.tools.SpringFXMLLoader;
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
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("ElectroStore - Вход");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadMainForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/main/mainForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("ElectroStore - Главная");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    private Stage getPrimaryStage() {
        return ElectroStoreApplication.primaryStage;
    }

    public void loadNewEquipmentForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/equipment/newEquipmentForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового оборудования");
    }

    public void loadEditEquipmentForm(Equipment selectedEquipment) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/equipment/editEquipmentForm.fxml");
        try {
            Parent editEquipmentFormRoot = fxmlLoader.load();
            EditEquipmentFormController controller = fxmlLoader.getController();
            controller.setEditEquipment(selectedEquipment);
            Scene scene = new Scene(editEquipmentFormRoot);
            getPrimaryStage().setTitle("ElectroStore - Редактирование оборудования");
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setResizable(false);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Parent loadMenuForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/menu/menuForm.fxml");
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSupplierForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/supplier/supplierForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового поставщика");
    }

    public void loadSupplierListForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/supplier/supplierList.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Список поставщиков");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadRegistrationForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/user/registrationForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Регистрация нового пользователя");
    }

    public void loadSelectedEquipmentForm(Equipment selectedEquipment) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/equipment/selectedEquipmentForm.fxml");
        try {
            Parent selectedEquipmentFormRoot = fxmlLoader.load();
            SelectedEquipmentFormController controller = fxmlLoader.getController();
            controller.setEquipment(selectedEquipment);

            Stage stage = new Stage();
            stage.setTitle("Информация об оборудовании");
            stage.setScene(new Scene(selectedEquipmentFormRoot));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void loadEditSupplierForm(Supplier selectedSupplier) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/supplier/editSupplierForm.fxml");
        try {
            Parent editSupplierFormRoot = fxmlLoader.load();
            EditSupplierFormController controller = fxmlLoader.getController();
            controller.setEditSupplier(selectedSupplier);
            Scene scene = new Scene(editSupplierFormRoot);
            getPrimaryStage().setTitle("ElectroStore - Редактирование поставщика");
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setResizable(false);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void loadNewCustomerForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/newCustomerForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового покупателя");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadCustomerListForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/customerList.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Список покупателей");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadEquipmentListForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/equipment/equipmentList.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Список оборудования");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }


    public void loadPurchaseForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/purchase/purchaseForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Покупка товара");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadIncomeForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/purchase/incomeForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Доход магазина");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadChangePasswordForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/user/ChangePasswordForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Смена пароля");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }



    public void loadRatingForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/purchase/ratingForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Рейтинг товаров");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }


    public void loadEditCustomerForm(Customer selectedCustomer) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/editCustomerForm.fxml");

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EditCustomerFormController controller = fxmlLoader.getController();

        if (controller != null) {
            controller.setCustomer(selectedCustomer);
            Scene scene = new Scene(root);
            getPrimaryStage().setTitle("Редактирование покупателя");
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setResizable(false);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } else {
            throw new RuntimeException("Контроллер не был инициализирован правильно.");
        }
    }
}



