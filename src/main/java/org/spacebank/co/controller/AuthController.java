package org.spacebank.co.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.spacebank.co.exception.AppException;
import org.spacebank.co.models.Role;
import org.spacebank.co.models.RoleName;
import org.spacebank.co.models.User;
import org.spacebank.co.payload.ApiResponse;
import org.spacebank.co.payload.JwtAuthenticationResponse;
import org.spacebank.co.payload.LoginRequest;
import org.spacebank.co.payload.SignUpRequest;
import org.spacebank.co.repository.RoleRepository;
import org.spacebank.co.repository.UserRepository;
import org.spacebank.co.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}
		User userDateils = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				signUpRequest.getPassword());

		userDateils.setPassword(passwordEncoder.encode(userDateils.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role not set."));

		userDateils.setRoles(Collections.singleton(userRole));

		User result = userRepository.save(userDateils);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}
}
