package com.shireesha.casestudy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shireesha.casestudy.models.User;
import com.shireesha.casestudy.utilities.DBQueries;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query(DBQueries.FINDEMAIL)
	public User findByEmail(String email);
	
}
