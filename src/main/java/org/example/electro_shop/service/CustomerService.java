package org.example.electro_shop.service;

import org.example.electro_shop.model.entity.Customer;
import org.example.electro_shop.model.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    public static Customer currentCustomer;

    public enum ROLES { CUSTOMER, MANAGER, ADMINISTRATOR }

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
        initSuperUser();
        initManager();
    }

    // Генерация суперпользователя (администратора)
    private void initSuperUser() {
        if (repository.count() > 0) {
            return;
        }
        Customer admin = new Customer();
        admin.setUsername("admin");
        admin.setPassword("12345");
        admin.setFirstname("Admin");
        admin.setLastname("SuperAdmin");
        admin.setBalance(0.0);
        admin.getRoles().add(ROLES.ADMINISTRATOR.toString());
        admin.getRoles().add(ROLES.CUSTOMER.toString());
        admin.getRoles().add(ROLES.MANAGER.toString());
        repository.save(admin);
    }


    private void initManager() {

        Optional<Customer> managerOptional = repository.findByUsername("manager");
        if (managerOptional.isPresent()) {
            return;
        }
        Customer manager = new Customer();
        manager.setUsername("manager");
        manager.setPassword("123");
        manager.setFirstname("Manager");
        manager.setLastname("DefaultManager");
        manager.setBalance(0.0);

        manager.getRoles().add(ROLES.MANAGER.toString());
        manager.getRoles().add(ROLES.CUSTOMER.toString());
        repository.save(manager);
    }

    public void add(Customer customer) {
        customer.getRoles().clear();
        customer.getRoles().add(ROLES.CUSTOMER.toString());
        repository.save(customer);
    }

    public Customer update(Customer customer) {
        return repository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    public boolean authenticate(String username, String password) {
        Optional<Customer> optionalCustomer = repository.findByUsername(username);
        if (optionalCustomer.isEmpty()) {
            return false;
        }
        Customer loginCustomer = optionalCustomer.get();
        if (!loginCustomer.getPassword().equals(password)) {
            return false;
        }
        currentCustomer = loginCustomer;
        return true;
    }

    public static boolean currentUserHasRole(ROLES role) {
        if (currentCustomer == null) {
            return false;
        }
        return currentCustomer.getRoles().contains(role.toString());
    }

    public static boolean currentUserHasAnyRole(ROLES... roles) {
        if (currentCustomer == null) {
            return false;
        }
        for (ROLES role : roles) {
            if (currentCustomer.getRoles().contains(role.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameTaken(String username) {
        return repository.findByUsername(username).isPresent();
    }

    // Метод удаления покупателя по id
    public void deleteCustomer(Long customerId) {
        repository.deleteById(customerId);
    }

    public Customer changePassword(Long userId, String newPassword) {
        // Если текущий пользователь не администратор, то он может менять пароль только для своего аккаунта
        if (!currentUserHasRole(ROLES.ADMINISTRATOR)) {
            if (currentCustomer == null || !currentCustomer.getId().equals(userId)) {

            }
        }
        // Находим пользователя по ID
        Customer user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        // Здесь можно добавить дополнительную валидацию нового пароля (например, длину и сложность)
        user.setPassword(newPassword);
        return repository.save(user);
    }
}
