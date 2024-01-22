package com.student_grievance_portal.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.student_grievance_portal.entity.NewGrievance;
import com.student_grievance_portal.entity.NewUser;
import com.student_grievance_portal.service.GrievanceService;

import jakarta.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class ManageGrievanceController {
    @Autowired
    private GrievanceService grievanceService;

    @GetMapping("/manage-grievance")
    public String getAllGrievances(Model model, HttpSession session) {
    	NewUser user = (NewUser) session.getAttribute("user");
    	if (user != null) {
        List<NewGrievance> grievances = grievanceService.getGrievancesByUserId(user.getId());
        model.addAttribute("grievances", grievances);
        return "manage-grievance";
    	}
    	return "existing-user";
    }
    
    @GetMapping("/delete-grievance/{id}")
    public String deleteGrievance(@PathVariable Long id,HttpSession session) {
    	NewUser user = (NewUser) session.getAttribute("user");
    	if (user != null) {
        grievanceService.deleteGrievanceById(id);
        return "redirect:/manage-grievance?deleted=" + id;
    	}
    	return "existing-user";
    }
    @GetMapping("/edit-grievance/{id}")
    public String editGrievanceForm(@PathVariable Long id, Model model, HttpSession session) {
    	NewUser user = (NewUser) session.getAttribute("user");
    	if (user != null) {
        NewGrievance grievance = grievanceService.getGrievanceById(id);
        model.addAttribute("grievance", grievance);
        return "edit-grievance";
    	}
    	return "existing-user";
    }
    
    @PostMapping("/edit-grievance/{id}")
    public String editGrievance(@PathVariable Long id, @ModelAttribute NewGrievance updatedGrievance,@RequestParam("attachments") MultipartFile attachments) throws IOException {
        NewGrievance existingGrievance = grievanceService.getGrievanceById(id);

        if (existingGrievance != null) {
            // Update the fields of the existing grievance with the new values
            existingGrievance.setType(updatedGrievance.getType());
            existingGrievance.setTitle(updatedGrievance.getTitle());
            existingGrievance.setDescription(updatedGrievance.getDescription());
            existingGrievance.setAttachments(attachments.getBytes());

            // Save the updated grievance
            grievanceService.saveGrievance(existingGrievance);
        }

        return "redirect:/manage-grievance?updated="+id;
    }

}
