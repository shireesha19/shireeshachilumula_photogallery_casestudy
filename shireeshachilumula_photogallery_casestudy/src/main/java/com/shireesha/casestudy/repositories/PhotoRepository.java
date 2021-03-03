package com.shireesha.casestudy.repositories;

import com.shireesha.casestudy.models.User;
import com.shireesha.casestudy.utilities.DBQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shireesha.casestudy.models.Photo;

import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
public interface PhotoRepository extends JpaRepository<Photo, Long> {
	 List<Photo> findByNameContainingAndUsersIn(String name, List<User> user);

	 List<Photo> findAllByTakenOnAndUsersIn(Date takeOn, List<User> user);

	 List<Photo> findAllByTakenOnBetweenAndUsersIn(Date takeOnStart, Date takeOnEnd, List<User> user);

	/// @Query(DBQueries.PHOTORANGEQUERY) List<Photo>
	// findAllWithTakenOnBefore(@Param("takenOn") Date takenOn);
}
