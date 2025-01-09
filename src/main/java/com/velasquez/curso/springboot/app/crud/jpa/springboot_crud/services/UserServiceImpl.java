package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.services;

import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.entities.User;
import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.entities.Role;
import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.repositories.RoleRepository;
import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        optionalRoleUser.ifPresent(roles::add); // Si es ROL USER, se añade
        if (user.isAdmin()) {
            Optional<Role> optionaRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionaRoleAdmin.ifPresent(roles::add);
        }
        user.setRoles(roles);
        //Encriptar password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
