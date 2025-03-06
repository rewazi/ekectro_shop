package com.example.demo.service;

import com.example.demo.model.entity.Brand;
import com.example.demo.model.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    private BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Optional<Brand> add(Brand brand) {
        return Optional.of(brandRepository.save(brand));
    }

    public List<Brand> getListBrands() {
        return brandRepository.findAll();
    }
}
