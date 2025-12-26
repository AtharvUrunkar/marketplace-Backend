package com.marketplace.security;

import com.marketplace.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final JwtProperties jwtProperties;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(
				jwtProperties.SECRET.getBytes(StandardCharsets.UTF_8)
		);
	}

	public String generateToken(String email, Role role) {
		return Jwts.builder()
				.setSubject(email)
				.claim("role", role.name())
				.setIssuedAt(new Date())
				.setExpiration(
						new Date(System.currentTimeMillis() + jwtProperties.EXPIRATION_TIME)
				)
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
}
