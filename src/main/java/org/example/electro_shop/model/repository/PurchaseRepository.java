package org.example.electro_shop.model.repository;

import org.example.electro_shop.model.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {


    @Query("select sum(p.totalPrice) from Purchase p where p.purchaseDate between :start and :end")
    Double getIncomeBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("select p.equipment, sum(p.quantity) as totalSold " +
            "from Purchase p " +
            "where p.purchaseDate between :start and :end " +
            "group by p.equipment " +
            "order by totalSold desc")
    List<Object[]> getTopEquipmentBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    @Query("select p.equipment, sum(p.quantity) as totalSold " +
            "from Purchase p " +
            "group by p.equipment " +
            "order by totalSold desc")
    List<Object[]> getTopEquipmentAllTime();




}
