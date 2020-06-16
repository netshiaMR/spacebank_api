package org.spacebank.co.event;

import java.util.UUID;

import org.spacebank.co.models.User;
import org.spacebank.co.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {

	@Override
	public void onApplicationEvent(OnRegistrationSuccessEvent event) {
		// TODO Auto-generated method stub
		
	}

//	@Autowired
//	private UserRepository UserRepository;
//	
//	@Autowired
//	private MessageSource messages;
//
//	@Autowired
//	private MailSender mailSender;
//
//	@Override
//	public void onApplicationEvent(OnRegistrationSuccessEvent event) {
//		confirmRegistration(event);
//	}
//
//	private void confirmRegistration(OnRegistrationSuccessEvent event) {
//		User user = event.getUser();
//		String token = UUID.randomUUID().toString();
////		UserRepository.createVerificationToken(user, token);
//
//		String recipient = user.getEmail();
//		String subject = "Registration Confirmation";
//		String url = event.getAppUrl() + "/confirmRegistration?token=" + token;
//		String message = messages.getMessage("message.registrationSuccessConfimationLink", null, event.getLocale());
//
//		SimpleMailMessage email = new SimpleMailMessage();
//		email.setTo(recipient);
//		email.setSubject(subject);
//		email.setText(message + "http://localhost:5000" + url);
//		System.out.println(url);
//		mailSender.send(email);
//	}
}
