package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserData {

	private int id;
	private String email;
	private String avatar;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;
}
