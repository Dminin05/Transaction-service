package com.minin.transaction_service.controllers;

import com.minin.transaction_service.dto.TransactionDto;
import com.minin.transaction_service.service.transactions.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public void createNewTransaction(@RequestBody TransactionDto.Request.Create requestDto) {
        transactionService.create(requestDto);
    }

}
