package com.minin.transaction_service.mappers;

import com.minin.transaction_service.dto.TransactionDto;
import com.minin.transaction_service.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "requestDto.fromAccount", target = "fromAccount")
    @Mapping(source = "requestDto.toAccount", target = "toAccount")
    @Mapping(source = "requestDto.currency", target = "currency")
    @Mapping(source = "requestDto.expenseCategory", target = "expenseCategory")
    @Mapping(source = "requestDto.date", target = "date")
    @Mapping(source = "requestDto.amount", target = "amount")
    Transaction toEntity(TransactionDto.Request.Create requestDto);

    List<TransactionDto.Response.Info> toTransactionsInfoList(List<Transaction> transactions);

}
