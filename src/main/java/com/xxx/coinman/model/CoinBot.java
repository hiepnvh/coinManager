package com.xxx.coinman.model;

import javax.persistence.*;

import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "coinbot")
public class CoinBot {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String coinCode;
    
    @Column(columnDefinition="double default '0.0'")
    private Double volume ;
    
    @Column(columnDefinition ="int(11) default 0")
    private int intervalTime ;
   
    private String platform;
    
    @Column(columnDefinition ="int(11) default '0'")
    private int sellLimit ;
    
    @Column(columnDefinition ="int(11) default '0'")
    private int buyLimit ;
    
    @Column(columnDefinition="double default '0.0'")
    private Double firstPrice;
    
    @Column(columnDefinition="boolean default '1'")
    private boolean active;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
