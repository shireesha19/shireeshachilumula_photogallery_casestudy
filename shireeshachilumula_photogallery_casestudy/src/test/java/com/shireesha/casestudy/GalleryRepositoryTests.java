package com.shireesha.casestudy;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
		String exepected="birds";
		String actual=null;
		User existinguser = userRepo.findByEmail("siri@gmail.com");
		if(existinguser!=null)
		{
		String name=existinguser.getEmail();
		List<User> users=Arrays.asList(existinguser);
		List<Gallery> gallery=gRepo.findByNameContainingAndUsersIn(exepected, users);
		//String actual="";
		System.out.println(gallery );
		System.out.println(gallery.size());
		if(!gallery.isEmpty())
		{
			actual=gallery.get(0).getName().toLowerCase();
			assertThat(actual).isEqualTo(exepected);
		}
        else
        {
        	
			assertNotEquals(actual,exepected);
        	
        }
		}
		else
		//assertThatExceptionOfType(null)
			assertThatNullPointerException();
	}
}
