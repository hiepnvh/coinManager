package com.xxx.coinman.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.xxx.coinman.model.Role;
import com.xxx.coinman.model.User;
import com.xxx.coinman.repository.RoleRepository;
import com.xxx.coinman.repository.UserRepository;

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
        user.setRoles(new HashSet<>(Arrays.asList(guest)));//Guest
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public User findByUserId(int uid) {
        return userRepository.findById(uid);
    }
    
}
