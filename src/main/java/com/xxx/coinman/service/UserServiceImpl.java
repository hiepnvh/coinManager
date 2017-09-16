package com.xxx.coinman.service;

import com.xxx.coinman.model.Role;
import com.xxx.coinman.model.User;
import com.xxx.coinman.repository.RoleRepository;
import com.xxx.coinman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //Role guest
        Role guest = roleRepository.findOne(2L);
        Set roles = new HashSet<>();
        roles.add(roles);
        user.setRoles(roles);//Guest
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
