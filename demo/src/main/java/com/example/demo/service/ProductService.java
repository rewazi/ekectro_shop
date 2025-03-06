package com.example.demo.service;

import com.example.demo.model.entity.Product;
import com.example.demo.model.repository.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void create(Product product) {
        productRepository.save(product);
    }

    public ObservableList<Product> getListProducts() {
        List<Product> products = productRepository.findAll();
        ObservableList<Product> listProducts = FXCollections.observableArrayList();
        listProducts.addAll(products);
        return listProducts;
    }
}
