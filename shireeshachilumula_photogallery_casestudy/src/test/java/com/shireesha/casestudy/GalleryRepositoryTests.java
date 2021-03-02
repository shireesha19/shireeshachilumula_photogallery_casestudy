package com.shireesha.casestudy;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.shireesha.casestudy.models.Gallery;
import com.shireesha.casestudy.models.User;
import com.shireesha.casestudy.repositories.GalleryRepository;
import com.shireesha.casestudy.repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class GalleryRepositoryTests {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private GalleryRepository gRepo;
	
	@Test
	public void testfindByNameContainingAndUsersIn() {
		String exepected="Animals";
		User existinguser = userRepo.findByEmail("siri@gmail.com");
		String name=existinguser.getEmail();
		List<User> users=Arrays.asList(existinguser);
		List<Gallery> gallery=gRepo.findByNameContainingAndUsersIn(exepected, users);
		String actual=gallery.get(0).getName();
	    boolean result=gallery.contains("Animals");
	    assertEquals(actual,exepected);
	  
	}
}
