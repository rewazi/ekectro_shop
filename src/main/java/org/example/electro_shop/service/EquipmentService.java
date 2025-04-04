package org.example.electro_shop.service;

import org.example.electro_shop.model.entity.Equipment;
import org.example.electro_shop.model.repository.EquipmentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public void create(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    public void update(Equipment equipment) {
        equipmentRepository.save(equipment);
    }


    public Equipment findById(Long id) {
        Optional<Equipment> optional = equipmentRepository.findById(id);
        return optional.orElse(null);
    }

    public ObservableList<Equipment> getAllEquipment() {
        List<Equipment> equipmentList = equipmentRepository.findAll();
        return FXCollections.observableArrayList(equipmentList);
    }

    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }
}
