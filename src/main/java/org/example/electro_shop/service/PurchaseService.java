package org.example.electro_shop.service;

import org.example.electro_shop.model.entity.Customer;
import org.example.electro_shop.model.entity.Product;
import org.example.electro_shop.model.entity.Purchase;
import org.example.electro_shop.model.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    public PurchaseService(PurchaseRepository purchaseRepository,
                           ProductService productService,
                           CustomerService customerService) {
        this.purchaseRepository = purchaseRepository;
        this.productService = productService;
        this.customerService = customerService;
    }


    public String buyEquipment(Long customerId, Long equipmentId, int quantity) {
        Customer customer = customerService.findById(customerId).orElse(null);
        Product product = productService.findById(equipmentId);
        if (customer == null) {
            return "Покупатель не найден!";
        }
        if (product == null) {
            return "Товар не найден!";
        }
        if (product.getQuantity() < quantity) {
            return "Недостаточно товара на складе!";
        }
        double totalPrice = product.getPrice() * quantity;
        if (customer.getBalance() < totalPrice) {
            return "Недостаточно средств у покупателя!";
        }

        customer.setBalance(customer.getBalance() - totalPrice);
        product.setStock(product.getStock() - quantity);
        customerService.update(customer);
        productService.update(product);

        Purchase purchase = new Purchase(product, customer, quantity, totalPrice, LocalDateTime.now());
        purchaseRepository.save(purchase);
        return "Покупка успешно выполнена!";
    }


    public double getIncome(LocalDateTime start, LocalDateTime end) {
        Double sum = purchaseRepository.getIncomeBetween(start, end);
        return (sum == null ? 0.0 : sum);
    }

    public double getIncomeByDay(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusNanos(1);
        return getIncome(start, end);
    }

    public double getIncomeByMonth(YearMonth ym) {
        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end = ym.atEndOfMonth().atTime(23, 59, 59, 999999999);
        return getIncome(start, end);
    }

    public double getIncomeByYear(int year) {
        LocalDateTime start = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(year, 12, 31).atTime(23, 59, 59, 999999999);
        return getIncome(start, end);
    }


    public List<Object[]> getTopEquipment(LocalDateTime start, LocalDateTime end) {
        return purchaseRepository.getTopEquipmentBetween(start, end);
    }


    public List<Object[]> getTopEquipmentAllTime() {
        return purchaseRepository.getTopEquipmentAllTime();
    }

    public List<Object[]> getTopEquipmentByDay(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusNanos(1);
        return purchaseRepository.getTopEquipmentBetween(start, end);
    }

    public List<Object[]> getTopEquipmentByMonth(YearMonth ym) {
        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end = ym.atEndOfMonth().atTime(23, 59, 59, 999999999);
        return purchaseRepository.getTopEquipmentBetween(start, end);
    }

    public List<Object[]> getTopEquipmentByYear(int year) {
        LocalDateTime start = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(year, 12, 31).atTime(23, 59, 59, 999999999);
        return purchaseRepository.getTopEquipmentBetween(start, end);
    }


    public List<Object[]> getTopEquipmentByWeek(LocalDate weekStart) {
        LocalDateTime start = weekStart.atStartOfDay();
        LocalDateTime end = weekStart.plusDays(7).atStartOfDay().minusNanos(1);
        return purchaseRepository.getTopEquipmentBetween(start, end);
    }
}
