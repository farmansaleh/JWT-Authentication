package com.jwt.security.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * @author Farman Saleh
 * @since 13/09/2023
 *
 */

@Service
public class JwtService {
	
	public static final String SECRET_KEY="5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(Map<String, Object> exractClaims,UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(exractClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();
	}

	public boolean isTokenValid(String token,UserDetails userDetails) {
		final String username = getUserNameFromToken(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}

	private Date getExpiration(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token,Function<Claims, T> claimResolver) {
		final Claims claim = getAllClaims(token);
		return claimResolver.apply(claim);
	}

	private Claims getAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	//Generating a safe HS256 Secret key
	//SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	//String secretString = Encoders.BASE64.encode(key.getEncoded());
}