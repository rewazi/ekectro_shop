package org.example.electro_shop.model.repository;

import org.example.electro_shop.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM equipment_suppliers WHERE supplier_id = :supplierId", nativeQuery = true)
    void deleteEquipmentSupplierLinks(@Param("supplierId") Long supplierId);
}
