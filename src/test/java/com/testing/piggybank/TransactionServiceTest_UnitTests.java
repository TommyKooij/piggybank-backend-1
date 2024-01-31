package com.testing.piggybank;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.h2.tools.Console;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.testing.piggybank.account.AccountService;
import com.testing.piggybank.helper.CurrencyConverterService;
import com.testing.piggybank.model.Account;
import com.testing.piggybank.model.Currency;
import com.testing.piggybank.model.Direction;
import com.testing.piggybank.model.Transaction;
import com.testing.piggybank.transaction.CreateTransactionRequest;
import com.testing.piggybank.transaction.TransactionRepository;
import com.testing.piggybank.transaction.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest_UnitTests {
	@Mock
	private TransactionRepository transactionRepository;
	@Mock
	private AccountService accountService;
	@Mock
	private CurrencyConverterService converterService;
	@InjectMocks
	private TransactionService transactionService;
	
	@Test
	public void filterAndLimitTest_emptyListCheck() {
		//List
		List<Transaction> transactionList = Collections.emptyList();
        long accountId = 1L;
        int limit = 2;
        
        //Act
        List<Transaction> result = transactionService.filterAndLimitTransactions(transactionList, accountId, limit);
        
        //Assert
        Assertions.assertEquals(0, result.size());
	}
	
	@Test
	public void createTransaction_calledSaveFunction() {
		//List
		Mockito.when(accountService.getAccount(1L)).thenReturn(Optional.of(new Account()));
		Mockito.when(accountService.getAccount(2L)).thenReturn(Optional.of(new Account()));
		Mockito.when(converterService.toEuro(Currency.EURO, new BigDecimal(200))).thenReturn(new BigDecimal(1));
		
		CreateTransactionRequest transactionRequest = new CreateTransactionRequest();
		transactionRequest.setSenderAccountId(1L);
		transactionRequest.setReceiverAccountId(2L); 
		transactionRequest.setAmount(new BigDecimal(200)); 
		transactionRequest.setCurrency(Currency.EURO);
		transactionRequest.setDescription("CreateTransaction Test"); 
		
		//Act
		transactionService.createTransaction(transactionRequest); 
		
		//Assert
		Mockito.verify(transactionRepository).save(Mockito.any());
	}
	
	@Test
	public void sortsDescByDateTime_returnedValue() {
		//List
		Transaction transaction1 = new Transaction();
		Transaction transaction2 = new Transaction();
		transaction1.setDateTime(Instant.now());
		transaction2.setDateTime(ZonedDateTime.now().minusYears(5).toInstant());
		
		//Act
		int result = transactionService.sortDescByDateTime(transaction1, transaction2);
		
		//Assert
		Assertions.assertNotNull(result);
		//Gives -1 if second time came before first, and 1 if second time came after first
		Assertions.assertTrue(result == -1);
	}
}
