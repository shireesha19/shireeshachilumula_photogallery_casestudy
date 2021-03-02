package com.shireesha.casestudy;

import static org.assertj.core.api.Assertions.assertThat;

import com.shireesha.casestudy.models.User;
import com.shireesha.casestudy.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository repo;
	
	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("testuser1@demo.com");
		user.setPassword("password");
		user.setFirstName("test");
		user.setLastName("user");
		String actual = user.getEmail();
		User savedUser = repo.save(user);
		User existUser = entityManager.find(User.class, savedUser.getId());
		String exepected = existUser.getEmail();
		assertEquals(actual, exepected);
	}
	
	@Test
	public void testFindByEmail() {
		String email = "testuser@demo.com";
		//User user = new User();
		//user.setEmail(email);
		//user.setPassword("password");
		//user.setFirstName("test");
		//user.setLastName("user");
		//User savedUser = repo.save(user);
		User foundUser = repo.findByEmail(email);
		assertThat(foundUser.getEmail()).isEqualTo(email);
	}
}
