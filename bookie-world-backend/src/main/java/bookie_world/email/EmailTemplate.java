package bookie_world.email;

import lombok.Getter;

@Getter
public enum EmailTemplate {

	NEW_ACCOUNT("new_account");

	private final String name;

	EmailTemplate(String name) {
		this.name = name;
	}

}
