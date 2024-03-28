package com.minin.transaction_service.repository;

import com.minin.transaction_service.model.Limit;
import com.minin.transaction_service.model.enums.ExpenseCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LimitsRepository extends JpaRepository<Limit, UUID> {

    List<Limit> findAllByAccountId(Long accountId);

    Optional<Limit> findFirstByAccountIdAndExpenseCategoryOrderByCreationDateDesc(Long accountId, ExpenseCategoryType expenseCategoryType);

}
