package org.example.electro_shop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import org.example.electro_shop.model.entity.Product;
import org.example.electro_shop.service.FormService;
import org.example.electro_shop.service.PurchaseService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingFormController {

    private final FormService formService;
    private final PurchaseService purchaseService;

    @FXML
    private TableView<EquipmentRating> tvRating;
    @FXML
    private TableColumn<EquipmentRating, String> tcEquipment;
    @FXML
    private TableColumn<EquipmentRating, String> tcTotalSold;

    public RatingFormController(FormService formService, PurchaseService purchaseService) {
        this.formService = formService;
        this.purchaseService = purchaseService;
    }

    @FXML
    public void initialize() {
        // Настройка столбцов таблицы
        tcEquipment.setCellValueFactory(cellData -> cellData.getValue().equipmentProperty());
        tcTotalSold.setCellValueFactory(cellData -> cellData.getValue().totalSoldProperty());

        // Загружаем рейтинг при инициализации
        loadAllTimeRating();
    }

    private void loadAllTimeRating() {
        ObservableList<EquipmentRating> ratings = FXCollections.observableArrayList();
        List<Object[]> results = purchaseService.getTopEquipmentAllTime();

        // Добавляем результаты в таблицу
        for (Object[] row : results) {
            String equipmentName = ((Product) row[0]).getName();
            int totalSold = ((Number) row[1]).intValue();
            ratings.add(new EquipmentRating(equipmentName, totalSold));
        }

        tvRating.setItems(ratings);
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка ввода");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class EquipmentRating {
        private final SimpleStringProperty equipment;
        private final SimpleStringProperty totalSold;

        public EquipmentRating(String equipment, int totalSold) {
            this.equipment = new SimpleStringProperty(equipment);
            this.totalSold = new SimpleStringProperty(String.valueOf(totalSold));
        }

        public String getEquipment() {
            return equipment.get();
        }

        public SimpleStringProperty equipmentProperty() {
            return equipment;
        }

        public String getTotalSold() {
            return totalSold.get();
        }

        public SimpleStringProperty totalSoldProperty() {
            return totalSold;
        }
    }
}
