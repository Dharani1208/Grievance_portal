package com.student_grievance_portal.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.student_grievance_portal.entity.GuestGrievance;
import com.student_grievance_portal.entity.NewGrievance;
import com.student_grievance_portal.entity.NewUser;
import com.student_grievance_portal.repository.GuestRepository;
import com.student_grievance_portal.service.GuestService;

import jakarta.servlet.http.HttpSession;

@Controller
public class GuestController {

	@Autowired
	private GuestRepository guestRepo;
	@Autowired
	private GuestService guestService;
	
	
	@GetMapping("/track")
	public String showTrackForm() {
		return "track";
	}
	
	 @PostMapping("/api/guest-grievances")
	 public String addGuestGrievance(@RequestParam("type") String type,
             @RequestParam("title") String title,
             @RequestParam("description") String description, @RequestParam("attachments") MultipartFile attachments,HttpSession session,Model model) {
	     try {
	    	 GuestGrievance guestGrievance=new GuestGrievance();
	    	
	        	 
	    	 guestGrievance.setSubmission_date(LocalDateTime.now());
	    	 guestGrievance.setType(type);
	    	 guestGrievance.setTitle(title);
	    	 guestGrievance.setDescription(description);
	            if (!attachments.isEmpty() && !isPDF(attachments)) {
	                model.addAttribute("file_error");
	                return "redirect:/guest-user?file_error";
	            }
	            if (!attachments.isEmpty()) {
	            	guestGrievance.setAttachments(attachments.getBytes());
	            }
	            
	           GuestGrievance guest = guestRepo.save(guestGrievance);
	           model.addAttribute("guest", guest);

	            return "redirect:/guest-user?submitted="+guestGrievance.getId();

	     } catch (Exception e) {
	         // Handle the exception appropriately (log it, display an error message, etc.)
	         e.printStackTrace();  // For demonstration purposes. Replace with appropriate logging.
	         return "redirect:/guest-user?error";  // Redirect with an error parameter
	     }
	 }
	 private boolean isPDF(MultipartFile file) {
		    String fileName = file.getOriginalFilename();
		    return fileName != null && fileName.toLowerCase().endsWith(".pdf");
		}
	 
	 @GetMapping("/track-guest-grievances")
	    public String trackGuestGrievances(@RequestParam(name = "Id") String id, Model model) {
	        try {
	            UUID uuid = UUID.fromString(id);

	            Optional<GuestGrievance> guestGrievanceOptional = guestService.getGuestGrievancesById(uuid);

	            if (guestGrievanceOptional.isPresent()) {
	                GuestGrievance guestGrievance = guestGrievanceOptional.get();
	                model.addAttribute("guestGrievance", guestGrievance);
	                return "track-guest-grievances";
	            } 
	            else
	            {
	                // Handle the case when GuestGrievance with the given ID is not found
	                // You can redirect to an error page or handle it based on your requirements
	                return "redirect:/track?error="+id; // Example: redirect to an error page
	            }

	           
	        } catch (IllegalArgumentException e) {
	            // Handle the case when the provided ID is not a valid UUID
	            // You can redirect to an error page or handle it based on your requirements
	            return "redirect:/track?error="+id; // Example: redirect to an error page
	        }
	    }

//	 @GetMapping("/delete-guest-grievance/{id}")
//	    public String deleteGrievance(@PathVariable UUID id) {
//	        guestService.deleteGuestGrievanceById(id);
//	        return "redirect:/track?deleted=" + id;
//
//	    }
	 
	 @GetMapping("/edit-guest-grievance/{id}")
	    public String editGuestGrievanceForm(@PathVariable UUID id, Model model) {
	        GuestGrievance gGrievance = guestService.getGustGrievanceById(id);
	        model.addAttribute("gGrievance", gGrievance);
	        return "edit-guest-grievance";
	    }
	    
	 @PostMapping("/edit-guest-grievance/{id}")
	 public String editGuestGrievance(
	         @PathVariable UUID id,
	         @RequestParam("type") String type,
	         @RequestParam("title") String title,
	         @RequestParam("description") String description,
	         @RequestParam(name = "attachments", required = false) MultipartFile attachments,Model model) throws IOException {

	     GuestGrievance existingGuestGrievance = guestService.getGustGrievanceById(id);

	     if (existingGuestGrievance != null) {
	         existingGuestGrievance.setType(type);
	         existingGuestGrievance.setTitle(title);
	         existingGuestGrievance.setDescription(description);
	         if (!attachments.isEmpty() && !isPDFfile(attachments)) {
	                model.addAttribute("file_error");
		             return "redirect:/track-guest-grievances?Id=" + existingGuestGrievance.getId() + "&file_error";
	            }
	         if (!attachments.isEmpty()) {
	             existingGuestGrievance.setAttachments(attachments.getBytes());
	         }

	         // Save the updated grievance
	         try {
	             // Save the updated grievance
	             guestService.saveGuestGrievance(existingGuestGrievance);
	             System.out.println("Updated id:" + existingGuestGrievance.getId());
//	             return "redirect:/track-guest-grievances?Id=" + existingGuestGrievance.getId() + "&updated";
	         } catch (Exception e) {
	             // Handle the exception appropriately (log it, display an error message, etc.)
	             e.printStackTrace();  // For demonstration purposes. Replace with appropriate logging.

	             // Add an error attribute to the model
	             model.addAttribute("error", "Failed to update the grievance. Please try again.");
	             return "redirect:/track-guest-grievances?Id=" + existingGuestGrievance.getId() + "&error";  // Redirect to an error page
	         }
	     }
	     return "redirect:/track-guest-grievances?Id=" + existingGuestGrievance.getId() + "&updated";
}
	 
	 private boolean isPDFfile(MultipartFile file) {
		    String fileName = file.getOriginalFilename();
		    return fileName != null && fileName.toLowerCase().endsWith(".pdf");
		}
	 }
