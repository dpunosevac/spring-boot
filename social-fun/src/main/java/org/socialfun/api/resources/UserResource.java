package org.socialfun.api.resources;

import java.util.Date;

import org.socialfun.utils.CustomJsonDateSerializer;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Relation(value = "user", collectionRelation = "users")
public class UserResource extends ResourceWithEmbeddeds {

	private final String email;
	private final String username;
	private final String password;
	private final String firstName;
	private final String lastName;
	private final Date birthdate;

	@JsonCreator
	public UserResource(@JsonProperty("email") String email, @JsonProperty("username") String username,
			@JsonProperty("password") String password, @JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName, @JsonProperty("birthdate") Date birthdate) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@JsonSerialize(using = CustomJsonDateSerializer.class)
	public Date getBirthdate() {
		return birthdate;
	}
}
