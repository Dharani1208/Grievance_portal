package com.student_grievance_portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.student_grievance_portal.entity.Admin;
import com.student_grievance_portal.entity.NewGrievance;
import com.student_grievance_portal.entity.NewUser;
import com.student_grievance_portal.repository.AdminRepository;
import com.student_grievance_portal.repository.GrievanceRepository;
import com.student_grievance_portal.repository.UserRepository;
import com.student_grievance_portal.service.GrievanceService;
import com.student_grievance_portal.service.GuestService;

import jakarta.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GrievanceService grievanceService;
    @Autowired
    private GrievanceRepository grievanceRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private GuestService guestService;;


    @GetMapping("/login")
    public String showLoginForm() {
        return "existing-user"; 
    }
    @GetMapping("/student-dashboard")
    public String showStudentDashboard(Model model, HttpSession session) {
    	NewUser user = (NewUser) session.getAttribute("user");
    	if (user != null) {
            Long userId = user.getId();

            // Get the count of active grievances
            long activeGrievanceCount = grievanceService.getActiveGrievanceCountForUser(userId);
            long allGrievanceCount = grievanceService.getAllGrievanceCountForUser(userId);
            long closedGrievanceCount = grievanceService.getClosedGrievanceCountForUser(userId);
            // Get the list of grievances submitted by the user
            List<NewGrievance> userGrievances = grievanceRepository.findBySubmittedBy_Id(userId);
            
            model.addAttribute("allGrievanceCount", allGrievanceCount);
            model.addAttribute("activeGrievanceCount", activeGrievanceCount);
            model.addAttribute("closedGrievanceCount", closedGrievanceCount);

            model.addAttribute("userGrievances", userGrievances);
    	
        return "student-dashboard"; 
    	}
    	return "existing-user";
    	
    }

    @GetMapping("/new-grievance")
    public String newGrievance(HttpSession session) {
    	NewUser user = (NewUser) session.getAttribute("user");
    	if(user != null)
    	{
        return "new-grievance"; 
    }
    	return "existing-user";
    }
    
   
    
    @GetMapping("/admin-dashboard")
    public String showAdminDashboard(Model model, HttpSession session) {
    	Admin admin = (Admin) session.getAttribute("admin");
    	if (admin != null) {
            

            // Get the count of active grievances
            long activeGrievanceCount = grievanceService.getActiveGrievanceCountForUser();
            long allGrievanceCount = grievanceService.getAllGrievanceCountForUser(); 
            long newGrievanceCount = grievanceService.getNewGrievanceCountForUser();
            long closedGrievanceCount = grievanceService.getClosedGrievanceCountForUser();
            long guestGrievanceCount = guestService.getAllGuestGrievanceCountForUser();
            // Get the list of grievances submitted by the user
            List<NewGrievance> userGrievances = grievanceRepository.findAll();
            
            model.addAttribute("newGrievanceCount", newGrievanceCount);
            model.addAttribute("allGrievanceCount", allGrievanceCount);
            model.addAttribute("activeGrievanceCount", activeGrievanceCount);
            model.addAttribute("closedGrievanceCount", closedGrievanceCount);
            model.addAttribute("guestGrievanceCount", guestGrievanceCount);

            model.addAttribute("userGrievances", userGrievances);
    	
        return "admin-dashboard"; 
    	}
    	return "existing-user";
    }
    
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // Try to find a user with the provided email in both NewUser and NewAdmin entities
        NewUser user = userRepository.findByEmail(username);
        Admin admin = adminRepository.findByEmail(username);

        if (user != null && user.isPasswordMatch(password)) {
            // Set user information in the session
            session.setAttribute("user", user);
      return "redirect:/student-dashboard"; 
            
        } else if (admin != null && admin.isPasswordMatch(password)) {
            // Set admin information in the session
            session.setAttribute("admin", admin);

            // Redirect to the admin dashboard
            return "redirect:/admin-dashboard";
        }

        // Add error message and return to the login page
        return "redirect:/login?error";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate session on logout
        session.invalidate();
        return "redirect:/existing-user";
    }
}
