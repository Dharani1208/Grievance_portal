package com.student_grievance_portal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student_grievance_portal.entity.GrievanceWithUserDTO;
import com.student_grievance_portal.entity.NewGrievance;
import com.student_grievance_portal.entity.NewUser;
import com.student_grievance_portal.repository.GrievanceRepository;

@Service
public class GrievanceService {
	 @Autowired
	    private GrievanceRepository grievanceRepository;

    public List<NewGrievance> getGrievancesByUserId(Long userId) {
        return grievanceRepository.findBySubmittedBy_Id(userId);
    }
    
    public List<NewGrievance> getAllGrievances() {
        return grievanceRepository.findAll();
    }
    public List<GrievanceWithUserDTO> getAllGrievancesWithUserDetails() {
        List<Object[]> result = grievanceRepository.getAllGrievancesWithUserDetails();

        List<GrievanceWithUserDTO> dtos = new ArrayList<>();
        for (Object[] row : result) {
            GrievanceWithUserDTO dto = new GrievanceWithUserDTO();
            dto.setGrievance((NewGrievance) row[0]);
            dto.setUser((NewUser) row[1]);
            dtos.add(dto);
        }

        return dtos;
    }


    
    public NewGrievance getGrievanceById(Long id) {
        return grievanceRepository.findById(id).orElse(null);
    }
    
    public void deleteGrievanceById(Long id) {
        grievanceRepository.deleteById(id);
    }
    public void saveGrievance(NewGrievance grievance) {
        grievanceRepository.save(grievance);
    }
    
    public long getActiveGrievanceCountForUser(Long userId) {
        List<String> statusList = Arrays.asList("Submitted", "In Progress");
        return grievanceRepository.countByStatusInAndSubmittedBy_Id(statusList, userId);
    }

    public long getClosedGrievanceCountForUser(Long userId) {
        return grievanceRepository.countByStatusAndSubmittedBy_Id("Closed", userId);
    }
    public long getAllGrievanceCountForUser(Long userId) {
        return grievanceRepository.countBySubmittedBy_Id(userId);
    }
    
    //for admin-dashboard
    public long getAllGrievanceCountForUser() {
        return grievanceRepository.count();
    }
    public long getNewGrievanceCountForUser() {
        return grievanceRepository.countByStatus("Submitted");
    }
    public long getClosedGrievanceCountForUser() {
        return grievanceRepository.countByStatus("Closed");
    }
    public long getActiveGrievanceCountForUser() {
        List<String> statusList = Arrays.asList("Submitted", "In Progress");
        return grievanceRepository.countByStatusIn(statusList);
    }

}
