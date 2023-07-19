package com.endava.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import model.User;

public class PostUserTest {

	@Test
	public void createUser() {
		given()
				.baseUri("https://reqres.in")
				.basePath("/api/users")
				.contentType(ContentType.JSON)
				.log().all()
				.when()
				.body("{\n"
						+ "   \"name\": \"user130722\",\n"
						+ "   \"job\": \"manager\"\n"
						+ "}")
				.post()
				.then()
				.log().all()
				.statusCode(201)
				.body("name", equalTo("user130722"))
				.body("job", equalTo("manager"))
				.body("id", notNullValue())
				.body("createdAt", startsWith("2022-07-13"));
	}

	@Test
	public void createUserStringBody() {
		String jsonBody = "{\n"
				+ "   \"name\": \"user130722\",\n"
				+ "   \"job\": \"manager\"\n"
				+ "}";

		given()
				.baseUri("https://reqres.in")
				.basePath("/api/users")
				.contentType(ContentType.JSON)
				.log().all()
				.when()
				.body(jsonBody)
				.post()
				.then()
				.log().all()
				.statusCode(201)
				.body("name", equalTo("user130722"))
				.body("job", equalTo("manager"))
				.body("id", notNullValue())
				.body("createdAt", startsWith("2022-07-13"));
	}

	@Test
	public void createUserMapBody() {
		Map<String, String> mapBody = new HashMap<>();
		mapBody.put("name", "user130722");
		mapBody.put("job", "manager");

		given()
				.baseUri("https://reqres.in")
				.basePath("/api/users")
				.contentType(ContentType.JSON)
				.log().all()
				.when()
				.body(mapBody)
				.post()
				.then()
				.log().all()
				.statusCode(201)
				.body("name", equalTo("user130722"))
				.body("job", equalTo("manager"))
				.body("id", notNullValue())
				.body("createdAt", startsWith("2022-07-13"));
	}

	@Test
	public void createUserFileBody() {
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api/users";

		File file = new File("src/main/resources/postPayload.json");

		given()
				.contentType(ContentType.JSON)
				.body(file)
				.when()
				.post()
				.then()
				.log().all()
				.statusCode(201)
				.body("name", equalTo("jane"))
				.body("job", equalTo("tester"))
				.body("id", notNullValue())
				.body("createdAt", startsWith("2022-07-13"));
	}

	@Test
	public void createUserPojo() {
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api/users";

		User newUser = new User();
		newUser.setName("jane");
		newUser.setJob("admin");

		given()
				.contentType(ContentType.JSON)
				.body(newUser)
				.when()
				.post()
				.then()
				.log().all()
				.statusCode(201)
				.body("name", equalTo("jane"))
				.body("job", equalTo("admin"))
				.body("id", notNullValue())
				.body("createdAt", startsWith("2022-07-13"));

	}

}
