package org.spacebank.co.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.spacebank.co.exception.AppException;
import org.spacebank.co.models.Role;
import org.spacebank.co.models.RoleName;
import org.spacebank.co.models.User;
import org.spacebank.co.payload.request.SignUpHttpRequest;
import org.spacebank.co.payload.request.SignInHttpRequest;
import org.spacebank.co.payload.response.ApiHttpResponse;
import org.spacebank.co.payload.response.JwtAuthenticationResponse;
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
@RequestMapping(value = "/api/auth")
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
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInHttpRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpHttpRequest signUpHttpRequest) {
		if (userRepository.existsByUsername(signUpHttpRequest.getUsername())) {
			return new ResponseEntity(new ApiHttpResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(signUpHttpRequest.getEmail())) {
			return new ResponseEntity(new ApiHttpResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}
		// more check 
		User userDateils = new User(signUpHttpRequest.getName() , signUpHttpRequest.getUsername(), signUpHttpRequest.getEmail(),
				signUpHttpRequest.getPassword());

		userDateils.setPassword(passwordEncoder.encode(userDateils.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role not set."));

		userDateils.setRoles(Collections.singleton(userRole));

		User result = userRepository.save(userDateils);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();

		return ResponseEntity.created(location).body(new ApiHttpResponse(true, "User registered successfully"));
	}
}
