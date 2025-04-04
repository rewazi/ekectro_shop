package org.example.electro_shop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.electro_shop.model.entity.Equipment;
import org.example.electro_shop.service.CustomerService;
import org.example.electro_shop.service.EquipmentService;
import org.example.electro_shop.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class EquipmentListController implements Initializable {

    private final EquipmentService equipmentService;
    private final FormService formService;

    @FXML
    private TableView<Equipment> tvEquipment;

    @FXML
    private TableColumn<Equipment, String> tcId;

    @FXML
    private TableColumn<Equipment, String> tcName;

    @FXML
    private TableColumn<Equipment, String> tcPrice;

    @FXML
    private TableColumn<Equipment, String> tcQuantity;

    @FXML
    private TableColumn<Equipment, String> tcStock;

    @FXML
    private Button deleteEquipmentButton;

    public EquipmentListController(EquipmentService equipmentService, FormService formService) {
        this.equipmentService = equipmentService;
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tcId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        tcPrice.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        tcQuantity.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
        tcStock.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));


        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        tvEquipment.setItems(FXCollections.observableArrayList(equipmentList));
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    @FXML
    private void editSelectedEquipment() {
        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            showAccessDeniedAlert("У вас нет прав для редактирования оборудования.");
            return;
        }
        Equipment selected = tvEquipment.getSelectionModel().getSelectedItem();
        if (selected != null) {
            formService.loadEditEquipmentForm(selected);
        } else {
            System.out.println("Оборудование не выбрано!");
        }
    }

    private void showAccessDeniedAlert(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Ошибка доступа");
        alert.setHeaderText("Редактирование запрещено");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void deleteSelectedEquipment() {
        if (!CustomerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            showAccessDeniedAlert("Нельзя вам это делать!");
            return;
        }
        Equipment selected = tvEquipment.getSelectionModel().getSelectedItem();
        if (selected != null) {
            equipmentService.delete(selected.getId());
            tvEquipment.setItems(FXCollections.observableArrayList(equipmentService.getAllEquipment()));
        } else {
            showAccessDeniedAlert("Оборудование не выбрано!");
        }
    }

}
