package com.testing.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.restassured.path.json.JsonPath;

public class GetUsersTest {

	@Test
	public void getUsers() {

		given()
				.baseUri("https://reqres.in")
				.basePath("/api/users")
				.log().all()
				.when()
				.get()
				.then()
				.log().all()
				.statusCode(HttpStatus.SC_OK);
	}

	@Test
	public void getUsersByPage() {

		given()
				.baseUri("https://reqres.in")
				.basePath("/api/users")
				.queryParam("page", 2)
				.log().all()
				.when()
				.get()
				.then()
				.log().all()
				.statusCode(HttpStatus.SC_OK);
	}

	@Test
	public void getUsersByPageValidations() {

		given()
				.baseUri("https://reqres.in")
				.basePath("/api/users")
				.queryParam("page", 2)
				.when()
				.get()
				.then()
				.log().body()
				.statusCode(HttpStatus.SC_OK)
				.body("page", equalTo(2), "per_page", equalTo(6), "total", equalTo(12), "total_pages", equalTo(2))
				.body("size()", is(6));
	}

	@Test
	public void getUsersByPageOtherValidations() {

		given()
				.baseUri("https://reqres.in")
				.log().all()
				.when()
				.get("/api/users?page=1")
				.then()
				.statusCode(HttpStatus.SC_OK)
				.body("data", hasSize(6),
						"data.id", hasItems(1, 2, 3),
						"data.first_name", hasItems("George", "Janet"),
						"data.last_name", hasItems("Bluth", "Weaver"),
						"data.avatar", hasItems("https://reqres.in/img/faces/1-image.jpg"));
	}

	@Test
	public void getResponseJasonPath() {
		String response = given()
				.baseUri("https://reqres.in")
				.log().all()
				.when()
				.get("/api/users?page=1")
				.thenReturn()
				.asString();

		JsonPath jsonResponse = new JsonPath(response);

		assertEquals(1, jsonResponse.getInt("page"));
		assertEquals(6, jsonResponse.getInt("per_page"));
		assertEquals(12, jsonResponse.getInt("total"));
		assertEquals(2, jsonResponse.getInt("total_pages"));

		assertTrue(jsonResponse.getList("data.id").contains(2));
		assertTrue(jsonResponse.getList("data.first_name").contains("Janet"));
		assertTrue(jsonResponse.getList("data.last_name").contains("Wong"));
	}
}
