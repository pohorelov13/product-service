package com.service.product;

import com.service.product.model.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.MongoDBContainer;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductApplicationTests {
	@LocalServerPort
	private Integer port;
	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void shouldCreateProduct() {
		Product product = Product.builder()
				.price(BigDecimal.valueOf(1000))
				.name("Iphone 15")
				.description("Iphone").build();

		String body = """
				{
				    "name": "%s",
				    "description": "%s",
				    "price": %s
				}
				""".formatted(product.getName(), product.getDescription(), product.getPrice());
	RestAssured.given()
			.contentType(ContentType.JSON)
			.body(body)
			.when()
			.post("api/product")
			.then()
			.statusCode(201)
			.body("id", notNullValue())
			.body("name", equalTo(product.getName()))
			.body("description", equalTo(product.getDescription()))
			.body("price", equalTo(1000));
	}


}
