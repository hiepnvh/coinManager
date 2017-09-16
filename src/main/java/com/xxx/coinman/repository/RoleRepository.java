package com.xxx.coinman.repository;

import com.xxx.coinman.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "roles", path = "role")
public interface RoleRepository extends JpaRepository<Role, Long>{
}
