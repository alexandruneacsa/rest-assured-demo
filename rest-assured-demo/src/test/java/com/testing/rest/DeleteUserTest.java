package com.testing.rest;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

public class DeleteUserTest {

	@Test
	public void deleteUser() {

		given()
				.baseUri("https://reqres.in")
				.basePath("/api/users/{id}")
				.pathParam("id", 478)
				.log().all()
				.when()
				.delete()
				.then()
				.log().all()
				.statusCode(HttpStatus.SC_NO_CONTENT);
	}
}
