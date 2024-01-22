package com.student_grievance_portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.student_grievance_portal.entity.NewUser;
import com.student_grievance_portal.service.UserService;

@org.springframework.stereotype.Controller
public class Controller {
	
	@Autowired
	private UserService userService;
	
	@GetMapping({"/", "/index"})
	public String indexPage()
	{
		return "index";
	}
	
	
	@GetMapping("/new-user")
	public String newUser()
	{
		
		return "new-user";
		
	}
	
	@GetMapping("/existing-user")
	public String existingUser()
	{
		
		return "existing-user";
		
	}
	
	@GetMapping("/guest-user")
	public String guestUser()
	{
		return "guest-user";
	}
	
	@PostMapping("/save")
	public String addUser(@ModelAttribute NewUser user) {
	    try {
	     	        // Save the user
	        userService.save(user);

	        return "redirect:/existing-user";
	    } catch (Exception e) {
	        	        e.printStackTrace();  // For demonstration purposes. Replace with appropriate logging.
	        
	        // You might want to add an error message to be displayed in the view
	        // and redirect to the same page or a specific error page.
	        return "redirect:/new-user?error";
	    }
	}

}
