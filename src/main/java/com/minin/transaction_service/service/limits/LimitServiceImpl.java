package com.minin.transaction_service.service.limits;

import com.minin.transaction_service.dto.LimitDto;
import com.minin.transaction_service.mappers.LimitMapper;
import com.minin.transaction_service.model.Currency;
import com.minin.transaction_service.model.Limit;
import com.minin.transaction_service.model.enums.ExpenseCategoryType;
import com.minin.transaction_service.repository.LimitsRepository;
import com.minin.transaction_service.utils.DateCompareUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LimitServiceImpl implements LimitService {

    private final LimitsRepository limitsRepository;
    private final LimitMapper limitMapper;

    private final DateCompareUtils dateCompareUtils;

    @Override
    public List<LimitDto.Response.FullInfo> findAllByAccountId(Long accountId) {
        return limitMapper.toFullInfoList(limitsRepository.findAllByAccountId(accountId));
    }

    @Override
    public Optional<Limit> findNewestByAccountIdAndExpenseCategory(Long accountId, ExpenseCategoryType expenseCategoryType) {
        return limitsRepository.findFirstByAccountIdAndExpenseCategoryOrderByCreationDateDesc(accountId, expenseCategoryType);
    }

    @Override
    public Limit create(LimitDto.Request.Create requestDto) {

        Date date = new Date();
        Limit newLimit = limitMapper.toEntity(requestDto, date, requestDto.getMonthLimit(), Currency.Type.USD);

        Limit previousLimit = limitsRepository.findFirstByAccountIdAndExpenseCategoryOrderByCreationDateDesc(requestDto.getAccountId(), requestDto.getExpenseCategory())
                .orElse(null);

        if (previousLimit != null) {

            if (dateCompareUtils.compareMonths(newLimit, previousLimit)) {

                BigDecimal restOfMonthLimit = previousLimit.getMonthLimit().subtract(previousLimit.getRestOfLimit());

                newLimit.setRestOfLimit(
                        newLimit.getMonthLimit().subtract(restOfMonthLimit)
                );
            }

        }

        return limitsRepository.save(newLimit);
    }

}
