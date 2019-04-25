package com.jessienwei.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jessienwei.web.dto.User;
import com.jessienwei.web.model.SignUpModel;
import com.jessienwei.web.service.UserService;

@Controller
public class RegisterController {

	@Autowired
	private UserService userService;
	
	@GetMapping(path="/signup")
    public ModelAndView showSignUp(ModelAndView modelAndView) {
		modelAndView.addObject("signUpModel", new SignUpModel());
		modelAndView.setViewName("signup");
		return modelAndView;
    }
	
	@PostMapping(path="/signup")
	public ModelAndView processSignUpForm(ModelAndView modelAndView, @Valid SignUpModel model, BindingResult bindingResult, HttpServletRequest request) {
		// Lookup user in database by e-mail
		User userExists = userService.findUserByEmail(model.getEmail());
		// If exists, send error message to the view
		if (userExists != null) {
			modelAndView.addObject("errorMessage", "Oops! There is already a user registered with the email provided.");
			modelAndView.setViewName("signup");
			bindingResult.reject("email");  //equals bindingResult.hasErrors()
		} 
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("signup");
		}else { 
			// create user in DB
			User user = new User();
			user.setName(model.getUsername());
			user.setEmail(model.getEmail());
			user.setPhone(model.getPhone());
			user.setPassword(model.getPassword());
			// Possible properties: confirmationToken, enabled, role, createdAt, updatedAt
					        
		    userService.saveUser(user);				
				
			modelAndView.addObject("confirmationMessage", "Congras! Registration completed successfully");
			modelAndView.addObject("signUpModel", new SignUpModel());
			modelAndView.setViewName("signup");
		}		
		return modelAndView;
	}	
	
}
