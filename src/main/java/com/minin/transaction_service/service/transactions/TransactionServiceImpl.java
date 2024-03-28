package com.minin.transaction_service.service.transactions;

import com.minin.transaction_service.dto.TransactionDto;
import com.minin.transaction_service.mappers.LimitMapper;
import com.minin.transaction_service.mappers.TransactionMapper;
import com.minin.transaction_service.model.Currency;
import com.minin.transaction_service.model.Limit;
import com.minin.transaction_service.model.Transaction;
import com.minin.transaction_service.repository.TransactionRepository;
import com.minin.transaction_service.service.currency.CurrencyService;
import com.minin.transaction_service.service.limits.LimitService;
import com.minin.transaction_service.utils.DateCompareUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    private final LimitService limitService;
    private final LimitMapper limitMapper;

    private final CurrencyService currencyService;

    private final DateCompareUtils dateCompareUtils;

    @Override
    public List<TransactionDto.Response.Info> findAllTransactionWhichLimitExceeded(Long accountId) {
        return transactionMapper.toTransactionsInfoList(transactionRepository.findAllByFromAccountAndLimitExceeded(accountId, true));
    }

    @Override
    @Transactional
    public Transaction create(TransactionDto.Request.Create requestDto) {

        Transaction transaction = transactionMapper.toEntity(requestDto);

        BigDecimal transactionAmountInUSD;
        if (transaction.getCurrency().equals(Currency.Type.USD)) {
            transactionAmountInUSD = transaction.getAmount();
        } else {
            transactionAmountInUSD = currencyService.convertCurrency(transaction.getCurrency(), Currency.Type.USD, transaction.getAmount());
        }

        Limit newestLimit =  limitService.findNewestByAccountIdAndExpenseCategory(requestDto.getFromAccount(), requestDto.getExpenseCategory())
                .orElse(null);

        if (newestLimit == null) {
            newestLimit = limitService.create(limitMapper.toCreateDto(requestDto.getFromAccount(), requestDto.getExpenseCategory(), new BigDecimal(1000)));
        }

        if (!dateCompareUtils.compareMonths(newestLimit, transaction.getDate())) {
            newestLimit.setUpdatedDate(transaction.getDate());
            newestLimit.setRestOfLimit(newestLimit.getMonthLimit());
        }

        newestLimit.setRestOfLimit(newestLimit.getRestOfLimit().subtract(transactionAmountInUSD));
        if (newestLimit.getRestOfLimit().compareTo(new BigDecimal(0)) < 0) {
            transaction.setLimitExceeded(true);
            transaction.setLimit(newestLimit);
        }

        return transactionRepository.save(transaction);
    }


}
