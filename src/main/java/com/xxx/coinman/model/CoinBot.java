package com.xxx.coinman.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "coinbot")
@EntityListeners(AuditingEntityListener.class)
public class CoinBot {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String coinCode;
    
    @Column(columnDefinition="double default '0.0'")
    private Double volume ;
    
//    @Column(columnDefinition ="int(11) default 0")
//    private int intervalTime ;
   
    private String platform;
    
    @Column(columnDefinition ="int(11) default '0'")
    private int sellLimit ;
    
    @Column(columnDefinition ="int(11) default '0'")
    private int buyLimit ;
    
    @Column(columnDefinition="double default '0.0'")
    private Double firstPrice;
    
    @Column(columnDefinition="double default '0.0'")
    private Double lastPrice;
    
    @Column(columnDefinition="double default '0.0'")
    private Double lastPriceGot;
    
    private String accountName;
    
    private String accountPassword;
    
    @Column(columnDefinition="boolean default '0'")
    private Boolean isBought;
    
    @Column(columnDefinition="boolean default '1'")
    private Boolean active;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId")
    private User user;
    
    @LastModifiedDate
    private Date lastModDate;

}
