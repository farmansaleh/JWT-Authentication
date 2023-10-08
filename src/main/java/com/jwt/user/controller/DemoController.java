package com.jwt.user.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/demo")
public class DemoController {
	
//	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/adminUrl")
	public ResponseEntity<String> admin(Principal principal){
		return ResponseEntity.ok("Hello! "+principal.getName() + "\n"+"from secure endpoint - admin");
	}
	
//	@PreAuthorize("hasRole('USER')")
	@GetMapping("/userUrl")
	public ResponseEntity<String> user(Principal principal){
		return ResponseEntity.ok("Hello! "+principal.getName() + "\n"+"from secure endpoint - user");
	}
	
}
