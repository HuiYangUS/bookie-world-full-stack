package bookie_world.user;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import bookie_world.role.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Using 'Spring Security' interfaces
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bw_users") // 'user' is a reserved keyword in SQL
@EntityListeners(AuditingEntityListener.class) // Capture 'Entity' data for auditing
public class User implements UserDetails, Principal {

	/**
	 * Check later
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String firstName;

	private String lastName;

	private LocalDate dateOfBirth;

	@Column(unique = true)
	private String email;

	private String password;

	private Boolean accountLocked;

	private Boolean accountEnabled;

	/**
	 * 'EAGER' fetch sets the highest priority to retrieve data
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdDate;

	/**
	 * Not created but to be updated
	 */
	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastUpdatedDate;

	@Override
	public String getName() {
		return this.email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.accountLocked;
	}

	@Override
	public boolean isEnabled() {
		return this.accountEnabled;
	}

	public String fullName() {
		return this.firstName + " " + this.lastName;
	}

}
