package com.testing.piggybank;
import com.testing.piggybank.model.Account;
import com.testing.piggybank.model.Transaction;
import com.testing.piggybank.transaction.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionService transactionService;
    @Test
    public void test() {
        
        // Transactielijst
        List<Transaction> transactionList = Collections.emptyList();
        long accountId = 1L;
        int limit = 2;
        // Act
        List<Transaction> result = transactionService.filterAndLimitTransactions(transactionList, accountId, limit);
        // Assertion
        Assertions.assertEquals(0, result.size());
    }
}
 