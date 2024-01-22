package com.student_grievance_portal.repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student_grievance_portal.entity.GuestGrievance;


public interface GuestRepository extends JpaRepository<GuestGrievance, UUID> {
	 Optional<GuestGrievance> findById(UUID Id);
	 long count();
}
