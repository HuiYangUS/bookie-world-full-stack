package bookie_world.user;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Token {

	@Id
	@GeneratedValue
	private Long id;

	private String token;

	private LocalDateTime createdAt;

	private LocalDateTime expiresAt;

	private LocalDateTime validatedAt;

	/**
	 * Column name 'user_id' (foreign key) in 'tokens' table
	 */
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
