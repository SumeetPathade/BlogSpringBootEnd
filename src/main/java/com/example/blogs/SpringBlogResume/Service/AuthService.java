package com.example.blogs.SpringBlogResume.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blogs.SpringBlogResume.Model.User;
import com.example.blogs.SpringBlogResume.Repository.UserRepository;
import com.example.blogs.SpringBlogResume.Secuirty.JwtProvider;
import com.example.blogs.SpringBlogResume.dto.LoginRequest;
import com.example.blogs.SpringBlogResume.dto.RegisterRequest;

@Service
public class AuthService {
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	public void signup(RegisterRequest registerRequest) {
		// TODO Auto-generated method stub
		User user=new User();
		
		user.setUserName(registerRequest.getUsername());
		user.setPassword(encodePassword((registerRequest.getPassword())));
		user.setEmail(registerRequest.getEmail());
		
		userRepository.save(user);
	}

	private String encodePassword(String password) {
		// TODO Auto-generated method stub
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
		
		System.out.println(passwordEncoder.encode(password));
		return passwordEncoder.encode(password);
		
	}
	
	public String login(LoginRequest loginRequest) {
		// TODO Auto-generated method stub
		System.out.println("login start");
		//User user= userRepository.findUserByUsername(loginRequest.getUsername());
			Authentication authenticate= authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername() , 
					loginRequest.getPassword()));
			System.out.println("after authenticate");
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			System.out.println("secquerty contex ");
			return jwtProvider.generateToken(authenticate);
			
			
	}

	
	/*public String login(LoginRequest loginRequest) {
		// TODO Auto-generated method stub
		System.out.println("login start");
		Optional<User> user= userRepository.findUserByUsername(loginRequest.getUsername());
			Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user , 
					encodePassword(loginRequest.getPassword())));
			System.out.println("after authenticate");
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			System.out.println("secquerty contex ");
			return jwtProvider.generateToken(authenticate);
			
			
	}*/
	
	
	public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
		org.springframework.security.core.userdetails.User principal = 
				(org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
	}

}
