package com.testing.piggybank;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.h2.tools.Console;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
	private TransactionService transactionService;
	@Mock
	private TransactionRepository transactionRepository;
	@Mock
	private AccountService accountService;
	@Mock
	private CurrencyConverterService converterService;
	
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
	public void createTransaction_transactionSuccessful() {
		//List
		CreateTransactionRequest transactionRequest = new CreateTransactionRequest();
		transactionRequest.setSenderAccountId(1L);
		transactionRequest.setReceiverAccountId(2L); 
		transactionRequest.setAmount(new BigDecimal("1.00")); 
		transactionRequest.setCurrency(Currency.EURO);
		transactionRequest.setDescription("CreateTransaction Test"); 
		//Act
		transactionService.createTransaction(transactionRequest); 
		List<Transaction> transactionResult = transactionRepository.findAllByReceiverAccount_Id(2L);
		//Assert
		Assertions.assertTrue(transactionResult.size() == 0); 
	}
}
