package com.example.Devops_lab;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DevopsLabApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void greetingShouldReturnDefaultMessage() {
		String result = this.restTemplate.getForObject("http://localhost:" + port + "/hello", String.class);
		assertThat(result).isEqualTo("Hello world"); // Assuming this is the expected response from your backend
	}
}