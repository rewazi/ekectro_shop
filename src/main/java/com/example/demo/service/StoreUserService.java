package com.example.demo.service;

import com.example.demo.model.entity.StoreUser;
import com.example.demo.model.repository.StoreUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreUserService {
    public static StoreUser currentUser;
    public enum ROLES { CUSTOMER, MANAGER, ADMINISTRATOR }
    private final StoreUserRepository repository;

    public StoreUserService(StoreUserRepository repository) {
        this.repository = repository;
        initSuperUser();
    }

    private void initSuperUser() {
        if (repository.count() > 0) return;
        StoreUser admin = new StoreUser();
        admin.setUsername("admin");
        admin.setPassword("12345");
        admin.setFirstname("Admin");
        admin.setLastname("Superadmin");
        admin.getRoles().add(ROLES.ADMINISTRATOR.toString());
        admin.getRoles().add(ROLES.MANAGER.toString());
        admin.getRoles().add(ROLES.CUSTOMER.toString());
        repository.save(admin);
    }

    public void add(StoreUser user) {
        if (user.getRoles().isEmpty()) {
            user.getRoles().add(ROLES.CUSTOMER.toString());  // По умолчанию роль CUSTOMER
        }
        repository.save(user);
    }

    public boolean authentication(String username, String password) {
        Optional<StoreUser> optionalUser = repository.findByUsername(username);
        if (optionalUser.isEmpty()) return false;
        StoreUser loginUser = optionalUser.get();
        if (!loginUser.getPassword().equals(password)) return false;
        currentUser = loginUser;
        return true;
    }

    public List<StoreUser> getAllCustomers() {
        return repository.findAll().stream()
                .filter(user -> user.getRoles().contains(ROLES.CUSTOMER.toString()))
                .collect(Collectors.toList());
    }
}
