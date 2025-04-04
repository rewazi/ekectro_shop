package org.example.electro_shop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.electro_shop.model.entity.Equipment;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.EquipmentService;
import org.example.electro_shop.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class MainFormController implements Initializable {

    private final FormService formService;
    private final EquipmentService equipmentService;

    @FXML
    private VBox vbMainFormRoot;

    @FXML
    private TableView<Equipment> tvEquipmentList;

    @FXML
    private TableColumn<Equipment, String> tcId;

    @FXML
    private TableColumn<Equipment, String> tcName;

    @FXML
    private TableColumn<Equipment, String> tcSuppliers;

    @FXML
    private TableColumn<Equipment, String> tcPrice;

    @FXML
    private TableColumn<Equipment, String> tcQuantity;

    @FXML
    private TableColumn<Equipment, String> tcStock;

    @FXML
    private HBox hbEditEquipment;

    public MainFormController(FormService formService, EquipmentService equipmentService) {
        this.formService = formService;
        this.equipmentService = equipmentService;
    }

    @FXML
    private void showEditEquipmentForm() {
        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка доступа");
            alert.setHeaderText("Редактирование запрещено");
            alert.setContentText("У вас нет прав для редактирования оборудования.");
            alert.showAndWait();
            return;
        }
        Equipment selectedEquipment = tvEquipmentList.getSelectionModel().getSelectedItem();
        if (selectedEquipment != null) {
            formService.loadEditEquipmentForm(selectedEquipment);
        } else {
            System.out.println("Оборудование не выбрано!");
        }
    }

    @FXML
    private void showSelectedEquipmentForm() {
        Equipment selectedEquipment = tvEquipmentList.getSelectionModel().getSelectedItem();
        if (selectedEquipment != null) {
            formService.loadSelectedEquipmentForm(selectedEquipment);
        } else {
            System.out.println("Оборудование не выбрано!");
        }
    }

    @FXML
    private void deleteSelectedEquipment() {
        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка доступа");
            alert.setHeaderText("Удаление запрещено");
            alert.setContentText("У вас нет прав для удаления оборудования.");
            alert.showAndWait();
            return;
        }
        Equipment selectedEquipment = tvEquipmentList.getSelectionModel().getSelectedItem();
        if (selectedEquipment != null) {
            equipmentService.delete(selectedEquipment.getId());
            tvEquipmentList.setItems(equipmentService.getAllEquipment());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Добавляем меню в начало главного окна
        vbMainFormRoot.getChildren().addFirst(formService.loadMenuForm());

        // Заполняем таблицу оборудованием
        tvEquipmentList.setItems(equipmentService.getAllEquipment());

        // Настраиваем колонки таблицы
        tcId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        tcSuppliers.setCellValueFactory(cellData -> {
            Equipment equipment = cellData.getValue();
            if (equipment.getSuppliers() == null || equipment.getSuppliers().isEmpty()) {
                return new SimpleStringProperty("");
            }
            String suppliers = equipment.getSuppliers().stream()
                    .map(supplier -> supplier.getName())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(suppliers);
        });
        tcPrice.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        tcQuantity.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
        tcStock.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));

        // При выборе строки отображаем панель редактирования
        tvEquipmentList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Equipment>() {
            @Override
            public void changed(ObservableValue<? extends Equipment> observable, Equipment oldValue, Equipment newValue) {
                hbEditEquipment.setVisible(newValue != null);
            }
        });

        // Обработчик двойного клика: при двойном клике по строке открывается подробная информация
        tvEquipmentList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Equipment selectedEquipment = tvEquipmentList.getSelectionModel().getSelectedItem();
                if (selectedEquipment != null) {
                    formService.loadSelectedEquipmentForm(selectedEquipment);
                }
            }
        });
    }
}
