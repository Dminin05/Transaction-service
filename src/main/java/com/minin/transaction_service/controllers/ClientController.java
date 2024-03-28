package com.minin.transaction_service.controllers;

import com.minin.transaction_service.dto.LimitDto;
import com.minin.transaction_service.dto.TransactionDto;
import com.minin.transaction_service.service.limits.LimitService;
import com.minin.transaction_service.service.transactions.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/client")
public class ClientController {

    private final TransactionService transactionService;
    private final LimitService limitService;

    @GetMapping("transactions/{accountId}")
    public List<TransactionDto.Response.Info> getTransactionsOverLimitByAccountID(@PathVariable Long accountId) {
        return transactionService.findAllTransactionWhichLimitExceeded(accountId);
    }

    @GetMapping("limits/{accountId}")
    public List<LimitDto.Response.FullInfo> getLimitsByAccountId(@PathVariable Long accountId) {
        return limitService.findAllByAccountId(accountId);
    }

    @PostMapping("limits")
    public void createNewLimit(@RequestBody LimitDto.Request.Create requestDto) {
        limitService.create(requestDto);
    }

}
