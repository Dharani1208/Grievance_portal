package com.student_grievance_portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.student_grievance_portal.entity.NewGrievance;

public interface GrievanceRepository extends JpaRepository<NewGrievance, Long>{

	long countByStatusInAndSubmittedBy_Id(List<String> statusList, Long userId);
	 long countByStatusIn(List<String> statusList);
	 long countByStatus(String status);
	long countByStatusAndSubmittedBy_Id(String status, Long userId);
	long countBySubmittedBy_Id(Long userId);
	long count();
	 List<NewGrievance> findBySubmittedBy_Id(Long userId);
	 @Query("SELECT g, u FROM NewGrievance g LEFT JOIN g.submittedBy u")
	    List<Object[]> getAllGrievancesWithUserDetails();

	 
}
