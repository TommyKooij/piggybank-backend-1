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
	void getTransaction_withValidTransactions() {
		//Arrange
		CreateTransactionRequest request = new CreateTransactionRequest();
		request.setCurrency(Currency.EURO);
		request.setReceiverAccountId(2L);
		request.setSenderAccountId(1L);
		request.setDescription("Test");
		request.setAmount(new BigDecimal(100));
		
		//Act
		transactionController.createTransaction(request);
		List<Transaction> transactions = transactionRepository.findAllByReceiverAccount_Id(2L);
		
		//Assert
		for(Transaction t : transactions){
			Currency currency = t.getCurrency();
			long receiverAccount = t.getReceiverAccount().getId();
			long senderAccount = t.getSenderAccount().getId();
			String description = t.getDescription();
			BigDecimal amount = t.getAmount();
			
			Assertions.assertEquals(currency, Currency.EURO);
			Assertions.assertEquals(receiverAccount, 2L);
			Assertions.assertEquals(senderAccount, 1L);
			Assertions.assertEquals(description, "Test");
			//Assertions.assertEquals(amount, new BigDecimal(100));
		}
	}
	
	@Test
	void createTransaction_withValidTransactions() {
		//Arrange
				CreateTransactionRequest request = new CreateTransactionRequest();
				request.setCurrency(Currency.EURO);
				request.setReceiverAccountId(2L);
				request.setSenderAccountId(1L);
				request.setDescription("Test");
				request.setAmount(new BigDecimal(100));
				
				//Act
				transactionController.createTransaction(request);
				List<Transaction> transactions = transactionRepository.findAllByReceiverAccount_Id(2L);
				
				//Assert
				Assertions.assertTrue(transactions.size() == 1);
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
		List<Transaction> transactions = transactionRepository.findAllByReceiverAccount_Id(3L);
		
		//Assert
		Assertions.assertTrue(transactions.size() == 1);
	}
}
