package com.minin.transaction_service.service.transactions;

import com.minin.transaction_service.dto.TransactionDto;
import com.minin.transaction_service.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction create(TransactionDto.Request.Create requestDto);

    List<TransactionDto.Response.Info> findAllTransactionWhichLimitExceeded(Long accountId);

}
