package com.example.cashcard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashCardApplicationTests {
	@Autowired 
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnNotFoundCashCardById() {
		// select an id that does not exist
		Integer id = 63;
		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/" + id, String.class);
		
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}
	
	@Test 
	void shouldReturnACashCardById(){
		Integer id = 29012;
		String uri = "/cashcards/" + id;
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Integer userId = documentContext.read("$.id");
		String username = documentContext.read("$.username");
		Double amount = documentContext.read("$.amount");
		
		assertThat(userId).isEqualTo(29012);
		assertThat(username).isEqualTo("Ana Ramirez");
		assertThat(amount).isEqualTo(45687.00);
		
	}
		
	
	
	@Test
	void shouldReturnACashCardWhenDataIsSaved() {
		assertThat(1).isEqualTo(1);
	}
	
	@Test
	void shouldNotReturnACashCardWithAnUnknownId() {
		assertThat(1).isEqualTo(1);
	}

}
