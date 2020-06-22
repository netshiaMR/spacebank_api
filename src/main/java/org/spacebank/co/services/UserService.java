package org.spacebank.co.services;

import java.sql.Date;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.spacebank.co.models.User;
import org.spacebank.co.models.VerificationToken;
import org.spacebank.co.repository.UserRepository;
import org.spacebank.co.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
	private static final int EXPIRATION = 60 * 24;
	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";

	@Autowired
	private UserRepository repository;

	@Autowired
	private VerificationTokenRepository tokenRepository;

	private boolean emailExist(String email) {
		return repository.findByEmail(email) != null;
	}

	public User getUser(String verificationToken) {
		User user = tokenRepository.findByToken(verificationToken).getUser();
		return user;
	}

	public VerificationToken getVerificationToken(String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}

	public void saveRegisteredUser(User user) {
		repository.save(user);
	}

	public void createVerificationToken(User user, String email, String token) {
		VerificationToken tokens = new VerificationToken();
		tokens.setUser(user);
		tokens.setEmail(email);
		tokens.setExpiryDate(calculateExpiryDate(EXPIRATION));
		tokens.setToken(token);
		tokens.setIsActived(false);
		tokenRepository.save(tokens);
	}

	public String validateVerificationToken(String token) {
		final VerificationToken verificationToken = tokenRepository.findByToken(token);
		if (verificationToken == null) {
			return TOKEN_INVALID;
		}

		final User user = verificationToken.getUser();
		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			tokenRepository.delete(verificationToken);
			return TOKEN_EXPIRED;
		}
		user.setEnabled(true);
		repository.save(user);
		return TOKEN_VALID;
	}

	public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
		VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
		vToken.setToken(UUID.randomUUID().toString());
		vToken.setExpiryDate(calculateExpiryDate(EXPIRATION));
		vToken = tokenRepository.save(vToken);
		return vToken;
	}

	public String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	public Date calculateExpiryDate(final int expiryTimeInMinutes) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new java.util.Date().getTime());
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}
}
