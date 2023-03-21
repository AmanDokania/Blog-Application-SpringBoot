package com.springboot.blog.springbootblogrestapi;

import com.springboot.blog.springbootblogrestapi.entity.Role;
import com.springboot.blog.springbootblogrestapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBlogRestApiApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args)  {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role user_role=Role.builder().name("ROLE_USER").build();
		Role admin_role=Role.builder().name("ROLE_ADMIN").build();

		if(!roleRepository.findByName("ROLE_USER").isPresent())
			roleRepository.save(user_role);
		if(!roleRepository.findByName("ROLE_ADMIN").isPresent())
			roleRepository.save(admin_role);
	}
}
