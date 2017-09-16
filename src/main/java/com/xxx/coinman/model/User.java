package com.xxx.coinman.model;

import javax.persistence.*;

import lombok.Data;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    private String username;
    
    private String password;
    
    @Transient
    private String passwordConfirm;
    
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<CoinBot> coinBots;
}
