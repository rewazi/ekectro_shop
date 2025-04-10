package org.example.electro_shop.service;

import org.example.electro_shop.model.entity.Product;
import org.example.electro_shop.model.repository.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void create(Product product) {
        productRepository.save(product);
    }

    public void update(Product product) {
        productRepository.save(product);
    }


    public Product findById(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        return optional.orElse(null);
    }

    public ObservableList<Product> getAllEquipment() {
        List<Product> productList = productRepository.findAll();
        return FXCollections.observableArrayList(productList);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
