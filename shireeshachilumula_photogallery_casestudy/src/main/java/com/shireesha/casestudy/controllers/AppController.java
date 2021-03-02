package com.shireesha.casestudy.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shireesha.casestudy.models.Gallery;
import com.shireesha.casestudy.models.Settings;
import com.shireesha.casestudy.models.User;
import com.shireesha.casestudy.repositories.GalleryRepository;
import com.shireesha.casestudy.repositories.SettingsRepository;
import com.shireesha.casestudy.repositories.UserRepository;
import com.shireesha.casestudy.services.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@SuppressWarnings("unused")
@Controller
public class AppController {

	private static final String BINDINGRESULT_USER = "org.springframework.validation.BindingResult.user";
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SettingsRepository settingsRepository;

	// loading index page
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

	// loading login page
	@GetMapping("/login")
	public String loginUser(Model model) {
		return "login";
	}

	// redirecting to user sign up Form
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "signup_form";
	}

	@PostMapping("/settings")
	public String userSettings(Settings settings, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// getting user details associated with Email
		User user = userRepo.findByEmail(userDetails.getUsername());
		user.setSettings(settings);
		// saving setting details into databse
		settingsRepository.save(settings);
		model.addAttribute("settings", settings);
		return "settings";
	}

	// get request for settings page
	@GetMapping("/settings")
	public String userSettings(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// getting user details associated with Email
		User user = userRepo.findByEmail(userDetails.getUsername());

		model.addAttribute("settings", user.getSettings());
		return "settings";
	}

	// processing user registration
	@PostMapping("/process_register")
    public String processRegister( @Valid @ModelAttribute("user") User user,BindingResult result,Model model ,RedirectAttributes attr) {
       // User existinguser = userRepo.findByEmail(email);
        //checking email id already exists or not
		
		  if (!result.hasErrors()) { 
			  
			  if (existinguser == null) { 
			  // implementation of
		  //Spring's PasswordEncoder interface //that uses the BCrypt strong hashing
		 // function to encode the password 
		  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); String encodedPassword =
		  passwordEncoder.encode(user.getPassword());
		  user.setPassword(encodedPassword); Settings settings = new Settings();
		  user.setSettings(settings); settings.setUser(user); //
		  settingsRepository.save(settings); //saving user registration details into
		 // database
		  userRepo.save(user);
		  return "register_success"; 
		  }
		  else {
		  model.addAttribute("message", "This email already exists!"); 
		  return
		  "signup_form";
		  
		  } } 
		  else { 
			  attr.addFlashAttribute(BINDINGRESULT_USER, result);
			  System.out.println("error");
			  return "signup_form";
		  }
		 
        if(result.hasErrors()) {
        	attr.addFlashAttribute(BINDINGRESULT_USER, result);
        	System.out.println("error");
        	return "signup_form";
        }
        else
        	return "";
        
       
    }

	/*
	 * @ResponseStatus(HttpStatus.BAD_REQUEST)
	 * 
	 * @ExceptionHandler(MethodArgumentNotValidException.class) public Map<String,
	 * String> handleValidationExceptions(MethodArgumentNotValidException ex) {
	 * Map<String, String> errors = new HashMap<>();
	 * ex.getBindingResult().getAllErrors().forEach((error) -> { String fieldName =
	 * ((FieldError) error).getField(); String errorMessage =
	 * error.getDefaultMessage(); errors.put(fieldName, errorMessage); }); return
	 * errors; }
	 */
	// processing logout page
	@GetMapping("/logoutsuccess")
	public String showLogoutPage() {
		return "logout";
	}
}
