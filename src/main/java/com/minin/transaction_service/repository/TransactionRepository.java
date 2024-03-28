package com.minin.transaction_service.repository;

import com.minin.transaction_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("select t from Transaction t join fetch t.limit where t.fromAccount = (:accountId) and t.limitExceeded = (:isLimitExceeded)")
    List<Transaction> findAllByFromAccountAndLimitExceeded(Long accountId, boolean isLimitExceeded);

}
