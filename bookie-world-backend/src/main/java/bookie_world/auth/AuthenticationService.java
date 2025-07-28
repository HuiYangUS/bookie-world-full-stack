package bookie_world.auth;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bookie_world.role.RoleRepository;
import bookie_world.user.User;
import bookie_world.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	/**
	 * Create a new user in 'users' table
	 * 
	 * @param request
	 */
	public void register(RegistrationRequest request) {
		var userRole = roleRepository.findByName("USER")
				.orElseThrow(() -> new IllegalStateException("No such role 'USER' was found for this user"));
		var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).accountLocked(false)
				.accountEnabled(false).roles(List.of(userRole)).build();
		userRepository.save(user);
	}

}
