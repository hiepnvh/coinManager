package com.xxx.coinman.controller;

import com.xxx.coinman.model.Role;
import com.xxx.coinman.model.User;
import com.xxx.coinman.repository.RoleRepository;
import com.xxx.coinman.service.SecurityService;
import com.xxx.coinman.service.UserService;
import com.xxx.coinman.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		User user = getCurrentUser();
		ModelAndView modelAndView = new ModelAndView();
		if (user != null) {
			Role userRole = roleRepository.findOne(1L);
			if(user.getRoles().contains(userRole)){
				modelAndView.setViewName("resources/admin/index");
			}else{
				modelAndView.setViewName("welcome");
			}
			modelAndView.addObject("currentUser", user);
			
		} else {
			modelAndView.setViewName("login");
		}
		return modelAndView;
	}
    
    protected User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());
		return user;
	}
}
