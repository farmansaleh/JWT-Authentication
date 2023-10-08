package com.jwt.security.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Farman Saleh
 * @since 13/09/2023
 *
 */

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired JwtService jwtService;
	@Autowired UserDetailsService userDetailsService;
	@Autowired @Qualifier("handlerExceptionResolver") private HandlerExceptionResolver exceptionResolver;

	// authentice every request before go to controller
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		// get token from header which start with bearer
		final String authHeader = request.getHeader("Authorization");
		String jwtToken, username;

		try {
			if (authHeader != null && authHeader.startsWith("Bearer ")) {

				jwtToken = authHeader.substring(7);
				// return username from token by jwtService
				username = jwtService.getUserNameFromToken(jwtToken);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

					if (jwtService.isTokenValid(jwtToken, userDetails)) {
						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
								username, null, userDetails.getAuthorities());
						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authToken);
					}
				}

			}

			filterChain.doFilter(request, response);
		} catch (

		Exception e) {
			exceptionResolver.resolveException(request, response, null, e);
		}
	}
}
