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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "coinbot")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class CoinBot {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String coinCode;
    
    private String refCode;
    
    @Column(columnDefinition="double default '0.0'", nullable = false)
    private Double firstVolume ;
    
    @Column(columnDefinition="double default '0.0'", nullable = false)
    private Double volume ;
    
    int roundTo;
    
    @Column(columnDefinition="double default '0.0'", nullable = false)
    private Double yourMoney ; // your money ($) in wallet
    
//    @Column(columnDefinition ="int(11) default 0")
//    private int intervalTime ;
   
    private String platform;
    
    @Column(columnDefinition ="int(11) default '0'", nullable = false)
    private int minProfit ;
    
    @Column(columnDefinition ="int(11) default '0'", nullable = false)
    private int maxLost ;
    
    @Column(columnDefinition="double default '0.0'", nullable = false)
    private Double firstPrice;
    
    @Column(columnDefinition="double default '0.0'", nullable = false)
    private Double lastPrice;
    
    @Column(columnDefinition="double default '0.0'", nullable = false)
    private Double lastPriceGot;
    
    private String apiKey;
    
    private String secretKey;
    
//    private String accountPassword;
    
    @Column(columnDefinition="boolean default '0'", nullable = false)
    private Boolean isBought = false;
    
    private String action;
    
    @Column(columnDefinition="boolean default '1'", nullable = false)
    private Boolean active = true;
    
    @NotAudited
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId")
    private User user;
    
    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    private Date lastModDate;

}
