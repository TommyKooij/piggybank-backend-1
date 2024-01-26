package com.testing.piggybank;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.testing.piggybank.model.Currency;
import com.testing.piggybank.model.Transaction;
import com.testing.piggybank.transaction.*;

//Integration Tests
@SpringBootTest
public class TransactionControllerTest_IntegrationTest {
    
	@Autowired
	TransactionController transactionController;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@BeforeEach
	void beforeEach() {
		transactionRepository.deleteAll();
	}
	
	@Test
	void getTransaction_withValidTransaction() {
		//Arrange
		CreateTransactionRequest request = new CreateTransactionRequest();
		request.setCurrency(Currency.USD);
		request.setReceiverAccountId(2L);
		request.setSenderAccountId(1L);
		request.setDescription("Test");
		request.setAmount(new BigDecimal(100));
		
		//Act
		transactionController.createTransaction(request);
		
		//Assert
		Currency currencyResult = request.getCurrency();
		Assertions.assertTrue(currencyResult == Currency.USD); 
		long receiverAccountResult = request.getReceiverAccountId();
		Assertions.assertTrue(receiverAccountResult == 2L); 
		long senderAccountResult = request.getSenderAccountId(); 
		Assertions.assertTrue(senderAccountResult == 1L); 
		String descriptionResult = request.getDescription();
		Assertions.assertTrue(descriptionResult == "Get Transaction Test"); 
		BigDecimal amountResult = request.getAmount();
		Assertions.assertEquals("100",amountResult.toString());
	}
	
	@Test
	void createTransaction_withValidTransaction() {
		//Arrange
		CreateTransactionRequest request = new CreateTransactionRequest();
		request.setCurrency(Currency.USD);
		request.setReceiverAccountId(2L);
		request.setSenderAccountId(1L);
		request.setDescription("Create Transaction Test");
		request.setAmount(new BigDecimal(100));
		
		//Act
		transactionController.createTransaction(request);
		
		//Assert
		List<Transaction> result = transactionRepository.findAllByReceiverAccount_Id(1);
		Assertions.assertEquals(1, result.size());
	}
	
	@Test
	void mapTransactions_toCorrectReceiverID() {
		//Arrange
		CreateTransactionRequest request = new CreateTransactionRequest();
		request.setCurrency(Currency.EURO);
		request.setReceiverAccountId(3L);
		request.setSenderAccountId(1L);
		request.setDescription("Map Transaction Test");
		request.setAmount(new BigDecimal(100));
		
		//Act
		transactionController.createTransaction(request);
		
		//Assert
		long receiver = request.getReceiverAccountId();
		Assertions.assertTrue(receiver == 3L);
		long sender = request.getSenderAccountId();
		Assertions.assertTrue(sender == 1L);
	}
}
