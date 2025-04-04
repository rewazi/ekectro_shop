package org.example.electro_shop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.electro_shop.service.FormService;
import org.example.electro_shop.service.PurchaseService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class IncomeFormController {

    private final PurchaseService purchaseService;
    private final FormService formService;

    @FXML
    private TextField tfStartDate;
    @FXML
    private TextField tfEndDate;
    @FXML
    private Label lblIncomeResult;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public IncomeFormController(PurchaseService purchaseService, FormService formService) {
        this.purchaseService = purchaseService;
        this.formService = formService;
    }

    @FXML
    private void handleCalculateIncome() {
        try {
            LocalDate startDate = LocalDate.parse(tfStartDate.getText().trim(), formatter);
            LocalDate endDate = LocalDate.parse(tfEndDate.getText().trim(), formatter);
            double income = purchaseService.getIncome(
                    startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay().minusNanos(1));
            lblIncomeResult.setText("Доход: " + income);
        } catch (Exception e) {
            lblIncomeResult.setText("Ошибка расчета дохода!");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }




}
