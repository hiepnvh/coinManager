package com.xxx.coinman.repository;

import com.xxx.coinman.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findById(int uid);
}
