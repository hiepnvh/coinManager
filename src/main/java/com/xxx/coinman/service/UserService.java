package com.xxx.coinman.service;

import com.xxx.coinman.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
