package com.marketplace.service;

import com.marketplace.dto.AuthResponse;
import com.marketplace.dto.LoginRequest;
import com.marketplace.dto.RegisterRequest;
import com.marketplace.entity.Role;
import com.marketplace.entity.User;
import com.marketplace.repository.UserRepository;
import com.marketplace.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.marketplace.exception.BadRequestException;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	public void register(RegisterRequest request) {
		User user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.CUSTOMER)
				.active(true)
				.build();

		userRepository.save(user);
	}

	public AuthResponse login(LoginRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new BadRequestException("Invalid email or password"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new BadRequestException("Invalid email or password");
		}

		String token = jwtTokenProvider.generateToken(
				user.getEmail(),
				user.getRole()
		);

		return new AuthResponse(token);
	}
}
