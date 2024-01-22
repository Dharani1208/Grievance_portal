package com.student_grievance_portal.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.student_grievance_portal.entity.Admin;
import com.student_grievance_portal.entity.GrievanceWithUserDTO;
import com.student_grievance_portal.entity.GuestGrievance;
import com.student_grievance_portal.entity.NewGrievance;
import com.student_grievance_portal.entity.NewUser;
import com.student_grievance_portal.service.AdminService;
import com.student_grievance_portal.service.GrievanceService;
import com.student_grievance_portal.service.GuestService;
import com.student_grievance_portal.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private GrievanceService grievanceService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private GuestService guestService;
	@Autowired
	private UserService userService;
	
	 
	@GetMapping("manage-all-grievance")
	public String getAllGrievancesAdmin(Model model, HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
    	if (admin != null) {

	    // Fetch grievances with associated user details
	    List<GrievanceWithUserDTO> grievancesWithUsers = grievanceService.getAllGrievancesWithUserDetails();

	    model.addAttribute("grievancesWithUsers", grievancesWithUsers);
	    return "manage-all-grievance";
    	}
    	return "existing-user";
	}

	@GetMapping("/update-status/{id}")
    public String updateGrievanceStatus(@PathVariable Long id, Model model,HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
    	if (admin != null) {
        NewGrievance grievance = grievanceService.getGrievanceById(id);
        model.addAttribute("grievance", grievance);
        return "update-status";
    	}
    	return "existing-user";
    }
	@PostMapping("/update-status/{id}")
	 public String updateStatus(@PathVariable Long id, @ModelAttribute NewGrievance updatedGrievance) {
        NewGrievance existingGrievance = grievanceService.getGrievanceById(id);

        if (existingGrievance != null) {
            // Update the fields of the existing grievance with the new values
            existingGrievance.setStatus(updatedGrievance.getStatus());
            existingGrievance.setRemarks(updatedGrievance.getRemarks());
            
            

            // Save the updated grievance
            grievanceService.saveGrievance(existingGrievance);
        }

        return "redirect:/manage-all-grievance?updated="+id;
    }
	
	@GetMapping("/update-guest-status/{id}")
    public String updateGuestGrievanceStatus(@PathVariable UUID id, Model model,HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
    	if (admin != null) {
        GuestGrievance grievance = guestService.getGustGrievanceById(id);
        model.addAttribute("grievance", grievance);
        return "update-guest-status";
    }
    	return "existing-user";
	}
	
	@PostMapping("/update-guest-status/{id}")
	 public String updateGuestStatus(@PathVariable UUID id, @ModelAttribute GuestGrievance updatedGuestGrievance) {
       GuestGrievance existingGrievance = guestService.getGustGrievanceById(id);

       if (existingGrievance != null) {
           // Update the fields of the existing grievance with the new values
           existingGrievance.setStatus(updatedGuestGrievance.getStatus());
           existingGrievance.setRemarks(updatedGuestGrievance.getRemarks());
           
           

           // Save the updated grievance
           guestService.saveGuestGrievance(existingGrievance);
       }

       return "redirect:/manage-guest-grievance?updated="+id;
   }
	
	
	@GetMapping("/manage-guest-grievance")
    public String getGuestGrievances( Model model, HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
    	if (admin != null) {
        try {
            

            List<GuestGrievance> guestGrievance = guestService.getAllGrievances();

                   
                model.addAttribute("guestGrievance", guestGrievance);
                return "manage-guest-grievance";
            
           
        } catch (Exception e) {
            
            return "redirect:/manage-guest-grievance?error"; // Example: redirect to an error page
        }
    }
    	return "existing-user";
	}
	
	
	@GetMapping("/add-admin")
	public String addNewAdmin(HttpSession session)
	{
		Admin admin = (Admin) session.getAttribute("admin");
    	if (admin != null) {
		return "add-admin";
	}
    	return "existing-user";
	}
	
	@PostMapping("/addAdmin")
	public String addAdmin(@ModelAttribute Admin admin) {
	    try {
	     	        // Save the user
	    	adminService.saveAdmin(admin);

	        return "redirect:/add-admin?success";
	    } catch (Exception e) {
	        	        e.printStackTrace();  // For demonstration purposes. Replace with appropriate logging.
	        
	        // You might want to add an error message to be displayed in the view
	        // and redirect to the same page or a specific error page.
	        return "redirect:/add-admin?error";
	    }
	}
	
	@GetMapping("/manage-admin")
    public String getAdmin( Model model, HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
    	if (admin != null) {
        try {
         
            List<Admin> adminUser = adminService.getAdmin();
 
                model.addAttribute("adminUser", adminUser);
                return "manage-admin";
            
           
        } catch (Exception e) {
            
            return "redirect:/manage-admin?error"; // Example: redirect to an error page
        }
    }
    	return "existing-user";
	}
	
	
	@GetMapping("/edit-admin/{id}")
    public String editAdminUser(@PathVariable Long id, Model model,HttpSession session) {
		Admin adminUser = (Admin) session.getAttribute("admin");
    	if (adminUser != null) {
        Admin admin = adminService.getAdminById(id);
        model.addAttribute("admin", admin);
        return "edit-admin";
    }return "existing-user";
	}
    	
	
	@PostMapping("/edit-admin/{id}")
	 public String editAdmin(@PathVariable Long id, @ModelAttribute Admin admin) {
      Admin adminUser = adminService.getAdminById(id);

      if (adminUser != null) {
          // Update the fields of the existing grievance with the new values
    	  adminUser.setName(admin.getName());
    	  adminUser.setEmail(admin.getEmail());
    	  adminUser.setPassword(admin.getPassword());
          

          // Save the updated grievance
    	  adminService.saveAdmin(adminUser);
      }

      return "redirect:/manage-admin?updated="+id;
  }
	
	@GetMapping("/delete-admin/{id}")
    public String deleteAdmin(@PathVariable Long id,HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
    	if (admin != null) {
        adminService.deleteAdmin(id);
        return "redirect:/manage-admin?deleted=" + id;

    }
    	return "existing-user";
	}
	
	@GetMapping("/manage-students")
    public String getStudents( Model model, HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
    	if (admin != null) {
        try {
         
            List<NewUser> userStudent = userService.getAllUsers();
 
                model.addAttribute("userStudent", userStudent);
                return "manage-students";
            
           
        } catch (Exception e) {
            
            return "redirect:/manage-students?error"; // Example: redirect to an error page
        }
    }
    	return "existing-user";
	}
	
	@GetMapping("/edit-student/{id}")
    public String editStudentUser(@PathVariable Long id, Model model,HttpSession session) {
		Admin adminUser = (Admin) session.getAttribute("admin");
    	if (adminUser != null) {
        NewUser user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit-student";
    }return "existing-user";
	}
	
	@PostMapping("/edit-student/{id}")
	 public String editStudent(@PathVariable Long id, @ModelAttribute NewUser user) {
		NewUser userStudent = userService.getUserById(id);

     if (userStudent != null) {
         // Update the fields of the existing grievance with the new values
    	 userStudent.setName(user.getName());
    	 userStudent.setEmail(user.getEmail());
    	 userStudent.setMobile(user.getMobile());
    	 userStudent.setCourse(user.getCourse());
    	 userStudent.setType(user.getType());
    	 userStudent.setPassword(user.getPassword());
         

         // Save the updated grievance
   	  userService.save(user);
     }

     return "redirect:/manage-students?updated="+id;
 }	
}
