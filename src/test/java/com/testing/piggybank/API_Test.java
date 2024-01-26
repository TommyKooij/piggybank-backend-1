package com.testing.piggybank;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.testing.piggybank.model.Currency;
import com.testing.piggybank.transaction.CreateTransactionRequest;
import com.testing.piggybank.transaction.GetTransactionsResponse;
import com.testing.piggybank.account.AccountResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class API_Test {
	@Autowired
	private TestRestTemplate restTemplate;
	
	//GET API
	@Test
	public void getAllTransactions() {
		//Arrange
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-User-Id", "1");
		
		//Act
		ResponseEntity<GetTransactionsResponse> response = restTemplate
				.getForEntity("/api/v1/transactions/1",GetTransactionsResponse.class);
		
		//Assert
		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	
	//POST API
	@Test
	public void createTransaction_responseOK() {
		//Arrange
		CreateTransactionRequest request = new CreateTransactionRequest();
		request.setCurrency(Currency.USD);
		request.setReceiverAccountId(2L);
		request.setSenderAccountId(1L);
		request.setDescription("Test");
		request.setAmount(new BigDecimal(100));
		
		//Act
		HttpEntity<CreateTransactionRequest> transactionRequest = new HttpEntity<>(request);
		
		//Assert
		ResponseEntity<HttpStatus> response = restTemplate.postForEntity("/api/v1/transactions", transactionRequest, HttpStatus.class);
		Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
	}
	
	//GET API
	@Test
	public void getAllUsers() {
		//Arrange
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-User-Id", "1");
		
		//Act
		ResponseEntity<AccountResponse> response = restTemplate
				.getForEntity("/api/v1/transactions/1",AccountResponse.class);
		
		//Assert
		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
	}
}
