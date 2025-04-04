package org.example.electro_shop.service;

import org.example.electro_shop.model.entity.Supplier;
import org.example.electro_shop.model.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier add(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long supplierId) {
        supplierRepository.deleteEquipmentSupplierLinks(supplierId);
        supplierRepository.deleteById(supplierId);
    }
}
