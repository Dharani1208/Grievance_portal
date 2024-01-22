package com.student_grievance_portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.student_grievance_portal.entity.NewUser;

@Repository
public interface UserRepository extends JpaRepository<NewUser, Long> {

	NewUser findByEmail(String email);
	Optional<NewUser> findById(Long id);
	

}
