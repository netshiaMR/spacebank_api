package org.spacebank.co.controller;

import java.net.URI;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spacebank.co.event.OnRegistrationSuccessEvent;
import org.spacebank.co.exception.AppException;
import org.spacebank.co.models.Role;
import org.spacebank.co.models.RoleName;
import org.spacebank.co.models.User;
import org.spacebank.co.payload.request.SignInHttpRequest;
import org.spacebank.co.payload.request.SignUpHttpRequest;
import org.spacebank.co.payload.response.ApiHttpResponse;
import org.spacebank.co.payload.response.JwtAuthenticationResponse;
import org.spacebank.co.repository.RoleRepository;
import org.spacebank.co.repository.UserRepository;
import org.spacebank.co.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

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

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInHttpRequest loginRequest) {
		// first check if the 
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(HttpServletRequest request, @Valid @RequestBody SignUpHttpRequest signUpHttpRequest) {
        LOGGER.debug("Registering user account with information: {}", signUpHttpRequest);
		if (userRepository.existsByUsername(signUpHttpRequest.getUsername())) {
			return new ResponseEntity(new ApiHttpResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(signUpHttpRequest.getEmail())) {
			return new ResponseEntity(new ApiHttpResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}

		User userDateils = new User(signUpHttpRequest.getFirstName(), signUpHttpRequest.getLastName(),
				signUpHttpRequest.getMiddleName(), signUpHttpRequest.getCellNumber(), signUpHttpRequest.getAddress(),
				signUpHttpRequest.getUsername(), signUpHttpRequest.getEmail(), signUpHttpRequest.getPassword());

		userDateils.setPassword(passwordEncoder.encode(userDateils.getPassword()));
		userDateils.setEnabled(false);
		
		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role not set."));

		userDateils.setRoles(Collections.singleton(userRole));

		User registered = userRepository.save(userDateils);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(registered.getUsername()).toUri();

		eventPublisher.publishEvent(new OnRegistrationSuccessEvent(registered, request.getLocale(), location.toString()));

		return ResponseEntity.created(location).body(new ApiHttpResponse(true, "User registered successfully"));
	}

	
	
}
