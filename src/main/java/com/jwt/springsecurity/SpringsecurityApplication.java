package com.jwt.springsecurity;

import com.jwt.springsecurity.entities.Role;
import com.jwt.springsecurity.entities.User;
import com.jwt.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringsecurityApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityApplication.class, args);
	}

	public void run(String... args) {

		User adminAccount = userRepository.findByRole(Role.ADMIN);

		if(adminAccount == null){
			User admin = new User();

			admin.setFirstname("admin");
			admin.setSecondname("admin");
			admin.setEmail("admin@gmail.com");
			admin.setRole(Role.ADMIN);
			admin.setPassword(new BCryptPasswordEncoder().encode("admin"));

			userRepository.save(admin);
		}

	}

}
