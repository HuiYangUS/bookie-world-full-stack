package bookie_world.auth;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bookie_world.email.EmailService;
import bookie_world.email.EmailTemplate;
import bookie_world.role.RoleRepository;
import bookie_world.user.Token;
import bookie_world.user.TokenRepository;
import bookie_world.user.User;
import bookie_world.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final EmailService emailService;

	@Value("${application.mailing.frontend.activation_url}")
	private String activationUrl;

	/**
	 * Create a new user in 'users' table
	 * 
	 * @param request
	 * @throws MessagingException
	 */
	public void register(RegistrationRequest request) throws MessagingException {
		var userRole = roleRepository.findByName("USER")
				.orElseThrow(() -> new IllegalStateException("No such role 'USER' was found for this user"));
		var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).accountLocked(false)
				.accountEnabled(false).roles(List.of(userRole)).build();
		userRepository.save(user);
		sendValidationEmail(user);
	}

	private void sendValidationEmail(User user) throws MessagingException {
		var activationToken = generateAndSaveActivationToken(user);
		emailService.sendEmail(user.getEmail(), user.fullName(), EmailTemplate.NEW_ACCOUNT, activationUrl,
				activationToken, "New Account Activation");
	}

	private String generateAndSaveActivationToken(User user) {
		String generatedCode = generateActivationCode(6);
		// Storage for validation of expiration of tokens
		var tokenEntity = Token.builder().token(generatedCode).createdAt(LocalDateTime.now())
				.expiresAt(LocalDateTime.now().plusMinutes(15)).user(user).build();
		tokenRepository.save(tokenEntity);
		return generatedCode;
	}

	private String generateActivationCode(int i) {
		String chars = "0123456789";
		StringBuilder codeBuilder = new StringBuilder();
		SecureRandom secureRandomizer = new SecureRandom();
		for (int z = 0; z < i; z++) {
			int randomIndex = secureRandomizer.nextInt(chars.length());
			codeBuilder.append(chars.charAt(randomIndex));
		}
		return codeBuilder.toString();
	}

}
