package com.jwt.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	JwtAuthFilter jwtAuthFilter;
	@Autowired
	JwtAuthEntryPoint jwtAuthEntryPoint;
	@Autowired
	AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// for jdk latest version with lamda function
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable()).authorizeHttpRequests(
				auth -> auth.requestMatchers("/auth/login", "/auth/register").permitAll().anyRequest().authenticated())
				//.exceptionHandling(ex-> ex.authenticationEntryPoint((JwtAuthEntryPoint) jwtAuthEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		// for old jdk version
//		http
//			.csrf().disable()
//			.authorizeHttpRequests()
//			.requestMatchers("/auth/login")
//			.permitAll()
//			.anyRequest().authenticated()
//			.and()
//			.sessionManagement()
//			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and()
//			.authenticationProvider(authenticationProvider)
//			.addFilterBefore(jwtAuthenticateFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
