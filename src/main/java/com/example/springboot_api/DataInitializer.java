package com.example.springboot_api;

import com.example.springboot_api.Model.Permission;
import com.example.springboot_api.Model.Role;
import com.example.springboot_api.repository.PermissionRepository;
import com.example.springboot_api.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {

        // 🔹 Permissions
        Permission readProduct = permissionRepository.findByName("READ_PRODUCT")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "READ_PRODUCT")));

        Permission createProduct = permissionRepository.findByName("CREATE_PRODUCT")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "CREATE_PRODUCT")));

        Permission deleteProduct = permissionRepository.findByName("DELETE_PRODUCT")
                .orElseGet(() -> permissionRepository.save(new Permission(null, "DELETE_PRODUCT")));

        // 🔹 Rôle USER
        roleRepository.findByName("USER").orElseGet(() -> {
            Role user = new Role();
            user.setName("USER");
            user.setPermissions(Set.of(readProduct));
            return roleRepository.save(user);
        });

        // 🔹 Rôle ADMIN
        roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role admin = new Role();
            admin.setName("ADMIN");
            admin.setPermissions(Set.of(readProduct, createProduct, deleteProduct));
            return roleRepository.save(admin);
        });
    }
}
