package org.spacebank.co.event;

import java.util.UUID;

import org.spacebank.co.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;


@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {
	@Autowired
	private org.spacebank.co.services.UserService UserService;

	@Autowired
	private MessageSource messages;

	@Autowired
	private MailSender mailSender;


    @Autowired
    private Environment env;
    
	@Override
	public void onApplicationEvent(OnRegistrationSuccessEvent event) {
		confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationSuccessEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		UserService.createVerificationToken(user, user.getEmail(), token);

		String recipient = user.getEmail();
		String subject = "Registration Confirmation";
		String url = event.getAppUrl() + "/confirmRegistration?token=" + token;
		String message = messages.getMessage("message.regSucc", null, "You registered successfully. We will send you a confirmation message to your email account.", event.getLocale());
		SimpleMailMessage email = new SimpleMailMessage();
	    email.setFrom(env.getProperty("support.email"));
		email.setTo(recipient);
		email.setSubject(subject);
		email.setText(message + "http:localhost:5000" + url);
		System.out.println(url);
		mailSender.send(email);
	}
}
