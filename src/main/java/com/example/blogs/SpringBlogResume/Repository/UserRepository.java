package com.example.blogs.SpringBlogResume.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blogs.SpringBlogResume.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findUserByUsername(String username);
	
}
