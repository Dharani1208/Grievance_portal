package com.student_grievance_portal.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import com.student_grievance_portal.entity.NewGrievance;
import com.student_grievance_portal.entity.NewUser;
import com.student_grievance_portal.repository.GrievanceRepository;
import com.student_grievance_portal.repository.UserRepository;


import jakarta.servlet.http.HttpSession;

@Controller
public class GrievanceController {

	

//		 @Autowired
//	    private GrievanceService grievanceService;
		 @Autowired
		 private GrievanceRepository grievanceRepo;
		 @Autowired
			private  UserRepository userRepository;
		 
		 
		 @PostMapping("/api/grievances")
		 public String addGrievance(@RequestParam("type") String type,
                 @RequestParam("title") String title,
                 @RequestParam("description") String description, @RequestParam("attachments") MultipartFile attachments,HttpSession session,Model model) {
		     try {
		    	 NewGrievance newGrievance=new NewGrievance();
		    	 String userEmail = ((NewUser) session.getAttribute("user")).getEmail();
		        	NewUser submittedBy = userRepository.findByEmail(userEmail);
		        	 newGrievance.setSubmittedBy(submittedBy);
		            newGrievance.setSubmissionDate(LocalDateTime.now());
		            newGrievance.setType(type);
		            newGrievance.setTitle(title);
		            newGrievance.setDescription(description);
		            if (!attachments.isEmpty() && !isPDF(attachments)) {
		                model.addAttribute("file_error");
		                return "redirect:/new-grievance?file_error";
		            }
		            if (!attachments.isEmpty()) {
		                newGrievance.setAttachments(attachments.getBytes());
		            }
		            
		            NewGrievance savedGrievance = grievanceRepo.save(newGrievance);
		            model.addAttribute("grievanceId", savedGrievance.getId());

		            return "redirect:/new-grievance?submitted=" + savedGrievance.getId();

		     } catch (Exception e) {
		         // Handle the exception appropriately (log it, display an error message, etc.)
		         e.printStackTrace();  // For demonstration purposes. Replace with appropriate logging.
		         return "redirect:/new-grievance?error";  // Redirect with an error parameter
		     }
		 }
		 private boolean isPDF(MultipartFile file) {
			    String fileName = file.getOriginalFilename();
			    return fileName != null && fileName.toLowerCase().endsWith(".pdf");
			}
		
	}

