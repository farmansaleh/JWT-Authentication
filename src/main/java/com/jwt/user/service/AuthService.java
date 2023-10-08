package com.jwt.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.security.config.AppConfig;
import com.jwt.security.config.JwtService;
import com.jwt.user.models.AuthRequest;
import com.jwt.user.models.AuthResponse;
import com.jwt.user.models.Role;
import com.jwt.user.models.User;
import com.jwt.user.repositories.UserRepo;

@Service
public class AuthService {
	
	@Autowired AppConfig appConfig;
	@Autowired UserRepo userRepo;
	@Autowired JwtService jwtService;
	@Autowired AuthenticationManager authenticationManager;
	
	public AuthResponse register(User user) {
		user.setPassword(appConfig.passwordEncoder().encode(user.getPassword()));
		user.setRole(Role.ADMIN);
		userRepo.save(user);
		var token = jwtService.generateToken(user);
		return new AuthResponse(token);
	}
	
	public AuthResponse login(AuthRequest authReq) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.getEmail(), authReq.getPassword()));
		var user = userRepo.findByEmail(authReq.getEmail()).orElseThrow(()->new UsernameNotFoundException("User not found"));
		var token = jwtService.generateToken(user);
		return new AuthResponse(token);
	}
	
}
