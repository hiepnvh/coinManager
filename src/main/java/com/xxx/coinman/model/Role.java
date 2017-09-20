package com.xxx.coinman.model;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    private String name;
    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
