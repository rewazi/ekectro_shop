package org.example.electro_shop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.electro_shop.model.entity.Equipment;
import org.example.electro_shop.service.FormService;
import org.example.electro_shop.service.PurchaseService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Component
public class RatingFormController {

    private final FormService formService;
    private final PurchaseService purchaseService;

    @FXML
    private ComboBox<String> cbPeriodType;
    @FXML
    private TextField tfPeriodValue;
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

        cbPeriodType.setItems(FXCollections.observableArrayList("Все время", "Год", "Месяц", "Неделя"));
        tfPeriodValue.setPromptText("Введите значение (например, 2025 или 3)");


        tcEquipment.setCellValueFactory(cellData -> cellData.getValue().equipmentProperty());
        tcTotalSold.setCellValueFactory(cellData -> cellData.getValue().totalSoldProperty());
    }

    @FXML
    private void handleCalculateRating() {
        String periodType = cbPeriodType.getValue();
        String periodValue = tfPeriodValue.getText();

        LocalDateTime start = null;
        LocalDateTime end = null;

        if ("Все время".equals(periodType)) {

        } else if ("Год".equals(periodType)) {
            try {
                int year = Integer.parseInt(periodValue);
                start = LocalDateTime.of(year, 1, 1, 0, 0);
                end = LocalDateTime.of(year, 12, 31, 23, 59, 59);
            } catch (NumberFormatException e) {
                showError("Введите корректный год.");
                return;
            }
        } else if ("Месяц".equals(periodType)) {
            try {
                int month = Integer.parseInt(periodValue);
                int currentYear = LocalDate.now().getYear();
                start = LocalDateTime.of(currentYear, month, 1, 0, 0);
                YearMonth ym = YearMonth.of(currentYear, month);
                end = LocalDateTime.of(currentYear, month, ym.lengthOfMonth(), 23, 59, 59);
            } catch (NumberFormatException e) {
                showError("Введите корректный номер месяца (1-12).");
                return;
            }
        } else if ("Неделя".equals(periodType)) {
            try {
                int weekNumber = Integer.parseInt(periodValue);

                LocalDate weekStart = LocalDate.now().with(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekNumber)
                        .with(java.time.DayOfWeek.MONDAY);
                start = weekStart.atStartOfDay();
                end = weekStart.plusDays(7).atStartOfDay().minusNanos(1);
            } catch (NumberFormatException e) {
                showError("Введите корректный номер недели.");
                return;
            }
        }

        ObservableList<EquipmentRating> ratings = FXCollections.observableArrayList();
        List<Object[]> results;
        if (start != null && end != null) {
            results = purchaseService.getTopEquipment(start, end);
        } else {
            results = purchaseService.getTopEquipmentAllTime();
        }

        for (Object[] row : results) {

            String equipmentName = ((Equipment) row[0]).getName();
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
