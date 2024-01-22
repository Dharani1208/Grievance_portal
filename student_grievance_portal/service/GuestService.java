package com.student_grievance_portal.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student_grievance_portal.entity.GuestGrievance;
import com.student_grievance_portal.entity.NewGrievance;
import com.student_grievance_portal.repository.GuestRepository;

@Service
public class GuestService {

	
	@Autowired
	private GuestRepository guestRepo;
	
	public Optional<GuestGrievance> getGuestGrievancesById(UUID Id) {
        return guestRepo.findById(Id);
    }
	
//	public void deleteGuestGrievanceById(UUID id) {
//		guestRepo.deleteById(id);
//    }
	
	public GuestGrievance getGustGrievanceById(UUID id) {
        return guestRepo.findById(id).orElse(null);
    }
	
	 public List<GuestGrievance> getAllGrievances() {
	        return guestRepo.findAll();
	    }
	 
	public void saveGuestGrievance(GuestGrievance gGrievance) {
        guestRepo.save(gGrievance);
    }
	
	//for admin dashboard
	 public long getAllGuestGrievanceCountForUser() {
	        return guestRepo.count();
	    }
}
