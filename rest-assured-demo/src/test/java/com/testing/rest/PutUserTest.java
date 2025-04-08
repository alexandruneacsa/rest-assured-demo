package com.testing.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import model.User;

public class PutUserTest {

	@Test
	public void updateUser() {

		var updatedUser = new User();
		updatedUser.setName("gheorghe");
		updatedUser.setJob("employee");

		given()
				.baseUri("https://reqres.in")
				.basePath("/api/users/{id}")
				.pathParam("id", 478)
				.contentType(ContentType.JSON)
				.log().all()
				.when()
				.body(updatedUser)
				.put()
				.then()
				.log().all()
				.statusCode(200)
				.body("name", equalTo("gheorghe"))
				.body("job", equalTo("employee"));
	}
}
