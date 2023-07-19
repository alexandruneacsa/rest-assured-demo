package com.endava.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import model.UserResponse;

public class GetUserTest {

	@Test
	public void getUser() {
		given()
				.baseUri("https://reqres.in")
				.basePath("/api/users/{id}")
				.pathParam("id", 12)
				.log().all()
				.when()
				.get()
				.then()
				.log().body()
				.statusCode(200);
	}

	@Test
	public void getUserValidations() {
		given()
				.baseUri("https://reqres.in")
				.basePath("/api/users/{id}")
				.pathParam("id", 12)
				.log().all()
				.when()
				.get()
				.then()
				.log().body()
				.statusCode(200)
				.body("data.id", equalTo(12))
				.body("data.email", equalTo("rachel.howell@reqres.in"));
	}

	@ParameterizedTest
	@CsvSource({
			"1, George",
			"2, Janet",
			"3, Emma"
	}
	)
	public void checkNameForUser(int userId, String expectedFirstName) {

		given()
				.baseUri("https://reqres.in")
				.basePath("/api/users/{userId}")
				.pathParam("userId", userId)
				.log().all()
				.when()
				.get()
				.then()
				.log().body()
				.assertThat()
				.body("data.first_name", equalTo(expectedFirstName));

	}

	@Test
	public void getUserPojo() {
		var user12 = given()
				.baseUri("https://reqres.in")
				.basePath("/api/users/{id}")
				.pathParam("id", 12)
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract()
				.as(UserResponse.class);

		assertThat(user12.getData().getId(), equalTo(12));
		assertThat(user12.getData().getLastName(), equalTo("Howell"));
		assertThat(user12.getSupport().getText(), startsWith("To keep ReqRes free"));
		assertThat(user12.getSupport().getUrl(), containsString("https://reqres.in/#support-heading"));

	}

}
