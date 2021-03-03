package com.shireesha.casestudy;

import com.shireesha.casestudy.models.User;
import com.shireesha.casestudy.models.Photo;
import com.shireesha.casestudy.repositories.PhotoRepository;
import com.shireesha.casestudy.repositories.UserRepository;
import com.shireesha.casestudy.services.CustomUserDetails;
import com.shireesha.casestudy.services.PhotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class PhotoRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PhotoRepository repo;

	@Autowired
	private UserRepository userRepo;
	

	@Test
	public void testfindByNameContainingAndUsersIn() {
		MockMultipartFile image = new MockMultipartFile("image", "", "application/json", "{\"image\": \"C:\\Users\\Public\\Pictures\\Sample Pictures\\Penguins.jpg\"}".getBytes());
        
		String ex="";
		User existinguser = userRepo.findByEmail("siri1@gmail.com");
		if(existinguser!=null) {
			
			List<User> users=Arrays.asList(existinguser);
			String actual=image.getName();
			List<Photo> photos = repo.findByNameContainingAndUsersIn("e",users);
			if(photos.size()==1&& photos.get(0)==null)
			{
				assertNotEquals(actual,ex);
			}
			else {
				 ex=photos.get(0).getName();
				 assertEquals(actual,ex);
			}
	}
	else {
		assertThatNullPointerException();
	}
		
		
	}

}
