package com.xxx.coinman.controller;

import com.xxx.coinman.model.CoinBot;
import com.xxx.coinman.model.User;
import com.xxx.coinman.repository.CoinBotRepository;
import com.xxx.coinman.service.SecurityService;
import com.xxx.coinman.service.UserService;
import com.xxx.coinman.validator.UserValidator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/coinbots")
public class CoinsController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private CoinBotRepository cbRepository;
    
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
//		cb.setFirstPrice(firstPrice);
		return cbRepository.save(cb);
		
    }

}
