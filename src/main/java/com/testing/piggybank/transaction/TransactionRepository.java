package com.testing.piggybank.transaction;

import com.testing.piggybank.model.Transaction;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	List<Transaction> findAllByReceiverAccount_Id(long l);
}
