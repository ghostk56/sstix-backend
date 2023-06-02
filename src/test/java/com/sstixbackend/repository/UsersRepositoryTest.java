package com.sstixbackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sstixbackend.model.Users;

@SpringBootTest
public class UsersRepositoryTest {
	
	@Autowired
	private UsersRepository ur;
	
	@Test
	void testSaveUser() {
		Users user = Users.builder().build();
		user.setUserName("apple");
		user.setPassword("aosd00asd-123132-asd213");
		user.setEmail("123asiodj@gmail.com");
		user.setPhone("0123-123-123");
		user.setStatus(1);
		user.setLevel(1);
		
		ur.save(user);
	}
	
}
