package com.minin.transaction_service.mappers;

import com.minin.transaction_service.dto.LimitDto;
import com.minin.transaction_service.model.Currency;
import com.minin.transaction_service.model.Limit;
import com.minin.transaction_service.model.enums.ExpenseCategoryType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LimitMapper {

    @Mapping(source = "requestDto.accountId", target = "accountId")
    @Mapping(source = "requestDto.expenseCategory", target = "expenseCategory")
    @Mapping(source = "requestDto.monthLimit", target = "monthLimit")
    @Mapping(source = "date", target = "creationDate")
    @Mapping(source = "date", target = "updatedDate")
    @Mapping(source = "currency", target = "currency")
    Limit toEntity(LimitDto.Request.Create requestDto, Date date, BigDecimal restOfLimit, Currency.Type currency);

    LimitDto.Request.Create toCreateDto(Long accountId, ExpenseCategoryType expenseCategory, BigDecimal monthLimit);

    LimitDto.Response.BaseInfo toBaseInfo(Limit limit);

    List<LimitDto.Response.FullInfo> toFullInfoList(List<Limit> limits);

}
