package com.jwt.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.user.models.AuthRequest;
import com.jwt.user.models.AuthResponse;
import com.jwt.user.models.User;
import com.jwt.user.service.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired AuthService authService;
	
	@PostMapping(value = "/register")
	public ResponseEntity<AuthResponse> register(@RequestBody User user) {
		return ResponseEntity.ok(authService.register(user));
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authReq) {
		return ResponseEntity.ok(authService.login(authReq));
	}
	
}
