package com.xxx.coinman.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "coinbot")
public class CoinBot {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String coinCode;
    
    private Double volume = 0.0;
    
    private int intervalTime = 0;
   
    private String platform;
    
    private int sellLimit = 0;
    
    private int buyLimit = 0;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
