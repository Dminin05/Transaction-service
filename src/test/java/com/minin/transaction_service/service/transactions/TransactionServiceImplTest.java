package com.minin.transaction_service.service.transactions;

import com.minin.transaction_service.dto.TransactionDto;
import com.minin.transaction_service.mappers.LimitMapper;
import com.minin.transaction_service.mappers.TransactionMapper;
import com.minin.transaction_service.model.Currency;
import com.minin.transaction_service.model.Limit;
import com.minin.transaction_service.model.Transaction;
import com.minin.transaction_service.model.enums.ExpenseCategoryType;
import com.minin.transaction_service.repository.TransactionRepository;
import com.minin.transaction_service.service.currency.CurrencyService;
import com.minin.transaction_service.service.limits.LimitService;
import com.minin.transaction_service.utils.DateCompareUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private LimitService limitService;
    @Mock
    private LimitMapper limitMapper;
    @Mock
    private CurrencyService currencyService;
    @Mock
    private DateCompareUtils dateCompareUtils;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void transaction_should_not_be_marked_1() {

        TransactionDto.Request.Create requestDto = new TransactionDto.Request.Create(
                123456789L,
                987654321L,
                Currency.Type.USD,
                ExpenseCategoryType.PRODUCT,
                new GregorianCalendar(2024, Calendar.FEBRUARY, 3).getTime(),
                new BigDecimal(1000)
        );

        Transaction transactionFromRequestDto = new Transaction(
                UUID.randomUUID(),
                requestDto.getFromAccount(),
                requestDto.getToAccount(),
                requestDto.getExpenseCategory(),
                requestDto.getCurrency(),
                requestDto.getAmount(),
                requestDto.getDate(),
                false,
                null
        );

        Limit limit = new Limit(
                UUID.randomUUID(),
                requestDto.getFromAccount(),
                requestDto.getExpenseCategory(),
                new BigDecimal(1000),
                new BigDecimal(1000),
                new GregorianCalendar(2024, Calendar.FEBRUARY, 1).getTime(),
                new GregorianCalendar(2024, Calendar.FEBRUARY, 1).getTime(),
                Currency.Type.USD
        );

        when(transactionMapper.toEntity(requestDto)).thenReturn(transactionFromRequestDto);

        when(limitService.findNewestByAccountIdAndExpenseCategory(transactionFromRequestDto.getFromAccount(), transactionFromRequestDto.getExpenseCategory()))
                .thenReturn(Optional.of(limit));

        when(dateCompareUtils.compareMonths(limit, transactionFromRequestDto.getDate())).thenReturn(true);

        when(transactionRepository.save(transactionFromRequestDto)).thenReturn(transactionFromRequestDto);

        Transaction transaction = transactionService.create(requestDto);

        assertEquals(limit.getRestOfLimit(), limit.getMonthLimit().subtract(transaction.getAmount()));
        assertFalse(transaction.isLimitExceeded());

    }

    @Test
    void transaction_should_not_be_marked_2() {

        TransactionDto.Request.Create requestDto = new TransactionDto.Request.Create(
                123456789L,
                987654321L,
                Currency.Type.USD,
                ExpenseCategoryType.PRODUCT,
                new GregorianCalendar(2024, Calendar.MARCH, 3).getTime(),
                new BigDecimal(100)
        );

        Transaction transactionFromRequestDto = new Transaction(
                UUID.randomUUID(),
                requestDto.getFromAccount(),
                requestDto.getToAccount(),
                requestDto.getExpenseCategory(),
                requestDto.getCurrency(),
                requestDto.getAmount(),
                requestDto.getDate(),
                false,
                null
        );

        Limit limit = new Limit(
                UUID.randomUUID(),
                requestDto.getFromAccount(),
                requestDto.getExpenseCategory(),
                new BigDecimal(1000),
                new BigDecimal(0),
                new GregorianCalendar(2024, Calendar.FEBRUARY, 1).getTime(),
                new GregorianCalendar(2024, Calendar.FEBRUARY, 1).getTime(),
                Currency.Type.USD
        );

        when(transactionMapper.toEntity(requestDto)).thenReturn(transactionFromRequestDto);

        when(limitService.findNewestByAccountIdAndExpenseCategory(transactionFromRequestDto.getFromAccount(), transactionFromRequestDto.getExpenseCategory()))
                .thenReturn(Optional.of(limit));

        when(dateCompareUtils.compareMonths(limit, transactionFromRequestDto.getDate())).thenReturn(false);

        when(transactionRepository.save(transactionFromRequestDto)).thenReturn(transactionFromRequestDto);

        Transaction transaction = transactionService.create(requestDto);

        assertEquals(limit.getRestOfLimit(), limit.getMonthLimit().subtract(transaction.getAmount()));
        assertFalse(transaction.isLimitExceeded());

    }

    @Test
    void transaction_should_be_marked_1() {

        TransactionDto.Request.Create requestDto = new TransactionDto.Request.Create(
                123456789L,
                987654321L,
                Currency.Type.USD,
                ExpenseCategoryType.PRODUCT,
                new GregorianCalendar(2024, Calendar.FEBRUARY, 3).getTime(),
                new BigDecimal(1001)
        );

        Transaction transactionFromRequestDto = new Transaction(
                UUID.randomUUID(),
                requestDto.getFromAccount(),
                requestDto.getToAccount(),
                requestDto.getExpenseCategory(),
                requestDto.getCurrency(),
                requestDto.getAmount(),
                requestDto.getDate(),
                false,
                null
        );

        Limit limit = new Limit(
                UUID.randomUUID(),
                requestDto.getFromAccount(),
                requestDto.getExpenseCategory(),
                new BigDecimal(1000),
                new BigDecimal(1000),
                new GregorianCalendar(2024, Calendar.FEBRUARY, 1).getTime(),
                new GregorianCalendar(2024, Calendar.FEBRUARY, 1).getTime(),
                Currency.Type.USD
        );

        when(transactionMapper.toEntity(requestDto)).thenReturn(transactionFromRequestDto);

        when(limitService.findNewestByAccountIdAndExpenseCategory(transactionFromRequestDto.getFromAccount(), transactionFromRequestDto.getExpenseCategory()))
                .thenReturn(Optional.of(limit));

        when(dateCompareUtils.compareMonths(limit, transactionFromRequestDto.getDate())).thenReturn(true);

        when(transactionRepository.save(transactionFromRequestDto)).thenReturn(transactionFromRequestDto);

        Transaction transaction = transactionService.create(requestDto);

        assertEquals(limit.getRestOfLimit(), limit.getMonthLimit().subtract(transaction.getAmount()));
        assertTrue(transaction.isLimitExceeded());

    }

    @Test
    void transaction_should_be_marked_2() {

        TransactionDto.Request.Create requestDto = new TransactionDto.Request.Create(
                123456789L,
                987654321L,
                Currency.Type.USD,
                ExpenseCategoryType.PRODUCT,
                new GregorianCalendar(2024, Calendar.MARCH, 3).getTime(),
                new BigDecimal(1001)
        );

        Transaction transactionFromRequestDto = new Transaction(
                UUID.randomUUID(),
                requestDto.getFromAccount(),
                requestDto.getToAccount(),
                requestDto.getExpenseCategory(),
                requestDto.getCurrency(),
                requestDto.getAmount(),
                requestDto.getDate(),
                false,
                null
        );

        Limit limit = new Limit(
                UUID.randomUUID(),
                requestDto.getFromAccount(),
                requestDto.getExpenseCategory(),
                new BigDecimal(1000),
                new BigDecimal(0),
                new GregorianCalendar(2024, Calendar.FEBRUARY, 1).getTime(),
                new GregorianCalendar(2024, Calendar.FEBRUARY, 1).getTime(),
                Currency.Type.USD
        );

        when(transactionMapper.toEntity(requestDto)).thenReturn(transactionFromRequestDto);

        when(limitService.findNewestByAccountIdAndExpenseCategory(transactionFromRequestDto.getFromAccount(), transactionFromRequestDto.getExpenseCategory()))
                .thenReturn(Optional.of(limit));

        when(dateCompareUtils.compareMonths(limit, transactionFromRequestDto.getDate())).thenReturn(false);

        when(transactionRepository.save(transactionFromRequestDto)).thenReturn(transactionFromRequestDto);

        Transaction transaction = transactionService.create(requestDto);

        assertEquals(limit.getRestOfLimit(), limit.getMonthLimit().subtract(transaction.getAmount()));
        assertTrue(transaction.isLimitExceeded());

    }

}