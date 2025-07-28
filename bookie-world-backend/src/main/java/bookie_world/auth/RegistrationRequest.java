package bookie_world.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RegistrationRequest {

	@NotEmpty(message = "First name is required")
	@NotBlank(message = "First name is required")
	private String firstName;

	@NotEmpty(message = "Last name is required")
	@NotBlank(message = "Last name is required")
	private String lastName;

	@NotEmpty(message = "User email is required")
	@NotBlank(message = "User email is required")
	@Email(message = "This email is not valid")
	private String email;

	@NotEmpty(message = "User password is required")
	@NotBlank(message = "User password is required")
	@Size(min = 6, max = 16, message = "This password is not valid")
	private String password;

}
