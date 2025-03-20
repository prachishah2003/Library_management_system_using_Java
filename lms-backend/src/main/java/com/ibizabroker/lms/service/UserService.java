package com.ibizabroker.lms.service;

import com.ibizabroker.lms.dao.RequestedBookRepository;
import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.dao.RoleRepository;
import com.ibizabroker.lms.dao.BorrowRepository;
import com.ibizabroker.lms.entity.Role;
import com.ibizabroker.lms.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private RequestedBookRepository requestedBookRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createAdminUserIfNotExists() {
        Optional<Users> existingAdmin = usersRepository.findByUsername("admin");

        if (!existingAdmin.isPresent()) {
            // Check if "Admin" role exists
            Optional<Role> adminRoleOptional = roleRepository.findByRoleName("Admin");
            Role adminRole;

            if (adminRoleOptional.isPresent()) {
                adminRole = adminRoleOptional.get();
            } else {
                // Create the "Admin" role if it does not exist
                adminRole = new Role();
                adminRole.setRoleName("Admin");
                adminRole = roleRepository.save(adminRole);
            }

            // Create the admin user
            Users adminUser = new Users();
            adminUser.setUsername("admin");
            adminUser.setName("Administrator");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Always hash passwords!

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            adminUser.setRole(roles);

            usersRepository.save(adminUser);
            System.out.println("✅ Admin user created successfully!");
        } else {
            System.out.println("✅ Admin user already exists!");
        }
    }



}
