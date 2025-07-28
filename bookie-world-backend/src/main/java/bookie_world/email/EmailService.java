package bookie_world.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import static org.springframework.mail.javamail.MimeMessageHelper.*;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.*;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;
	// Thymeleaf: html skeleton
	private final SpringTemplateEngine templateEngine;

	@Async
	public void sendEmail(String to, String email, EmailTemplate emailTemplate, String confirmURL,
			String activationCode, String subject) throws MessagingException {
		String targetTemplate = emailTemplate.name();
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED, UTF_8.name());
		Map<String, Object> properties = new HashMap<>();
		properties.put("username", email);
		properties.put("confirmation_url", confirmURL);
		properties.put("activation_code", activationCode);

		// Thymeleaf
		Context context = new Context();
		context.setVariables(properties);

		// Temp: fix later
		helper.setFrom("hui.yang.demotest@gmail.com");
		helper.setTo(to);
		helper.setSubject(subject);

		String targetTemplateV2 = templateEngine.process(targetTemplate, context);
		helper.setText(targetTemplateV2, true);
		mailSender.send(mimeMessage);
	}

}
