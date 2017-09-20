package com.xxx.coinman.model;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    private String username;
    
    private String password;
    
    @Transient
    private String passwordConfirm;
    
    @Email
    private String email;
    
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<CoinBot> coinBots;
}
