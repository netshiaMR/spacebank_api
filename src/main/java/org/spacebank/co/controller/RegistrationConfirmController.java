package org.spacebank.co.controller;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spacebank.co.models.User;
import org.spacebank.co.models.VerificationToken;
import org.spacebank.co.payload.response.ApiHttpResponse;
import org.spacebank.co.repository.UserRepository;
import org.spacebank.co.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/confirm")
public class RegistrationConfirmController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;

	@GetMapping("/registrationConfirm")
	public ApiHttpResponse confirmRegistration(final HttpServletRequest request, @RequestParam("token") final String token)
			throws UnsupportedEncodingException {
		Locale locale = request.getLocale();
		final String result = userService.validateVerificationToken(token);
		if (result.equals("valid")) {
			final User user = userService.getUser(token);
			return new ApiHttpResponse(HttpStatus.OK, messages.getMessage("message.accountVerified", null, locale));
		}
		return new ApiHttpResponse(HttpStatus.CONFLICT, messages.getMessage("auth.message." + result, null, locale));
	}

	@GetMapping("/user/resendRegistrationToken")
	public @ResponseBody ApiHttpResponse resendRegistrationToken(final HttpServletRequest request,
			@RequestParam("token") final String existingToken) {
		VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
		User user = userService.getUser(newToken.getToken());
		mailSender.send(constructResendVerificationTokenEmail(userService.getAppUrl(request), request.getLocale(),
				newToken, user));
        return new ApiHttpResponse(messages.getMessage("message.resendToken", null, request.getLocale()));
	}

	@PostMapping("/user/resetPassword")
	public @ResponseBody ApiHttpResponse resetPassword(final HttpServletRequest request,
			@RequestParam("email") final String userEmail) {
		return null;
	}

	private SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final Locale locale,
			final VerificationToken newToken, final User user) {
		final String confirmationUrl = contextPath + "/registrationConfirm.html?token=" + newToken.getToken();
		final String message = messages.getMessage("message.resendToken", null, locale);
		return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
	}

	private SimpleMailMessage constructEmail(String subject, String body, User user) {
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email"));
		return email;
	}
}
