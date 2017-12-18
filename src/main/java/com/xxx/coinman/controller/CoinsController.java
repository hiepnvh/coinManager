package com.xxx.coinman.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xxx.coinman.AppScheduler;
import com.xxx.coinman.model.CoinBot;
import com.xxx.coinman.model.User;
import com.xxx.coinman.repository.CoinBotRepository;
import com.xxx.coinman.service.UserService;

@RestController
@RequestMapping(value = "/coinbots")
public class CoinsController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private CoinBotRepository cbRepository;
    
    @Autowired
    EntityManager entityManager;
    
    @Autowired
	private MailSender mailSender;
	
	Logger LOGGER = Logger.getLogger(AppScheduler.class);
    
    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseBody
    public List<CoinBot> searchByUserId(){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());
		System.out.println(cbRepository.findByUser(user));
		
		return cbRepository.findByUser(user);
    }
    
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public CoinBot saveCB(@RequestBody CoinBot cb){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());
		cb.setUser(user);
		
		//set all to null when disable coin (so, when u back to this coin, it will be the first time)
		if(!cb.getActive()){
			cb.setAction(null);
			cb.setFirstPrice(null);
			cb.setFirstVolume(0.0);
			cb.setIsBought(false);
			cb.setYourMoney(0.0);
			cb.setVolume(0.0);
			cb.setLastPriceGot(0.0);
			cb.setLastPrice(0.0);
		}
//		cb.setFirstPrice(firstPrice);
		return cbRepository.save(cb);
		
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "viewlog", method = RequestMethod.GET)
    public List<CoinBot> viewLog(@RequestParam(name = "coincode", defaultValue="") String coinCode){
    	AuditReader reader = AuditReaderFactory.get(entityManager);
    	List<CoinBot> query = reader.createQuery()
    			.forRevisionsOfEntity(CoinBot.class, true, false)
    			.add(AuditEntity.property("coinCode").eq(coinCode))
    			.add(AuditEntity.property("action").isNotNull())
    			.addOrder(AuditEntity.revisionNumber().desc())
    			.setMaxResults(10)
    			.getResultList();
    	
    	
    	return query;
    }

}
