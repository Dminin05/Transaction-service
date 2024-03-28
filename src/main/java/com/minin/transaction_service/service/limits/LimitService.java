package com.minin.transaction_service.service.limits;

import com.minin.transaction_service.dto.LimitDto;
import com.minin.transaction_service.model.Limit;
import com.minin.transaction_service.model.enums.ExpenseCategoryType;

import java.util.List;
import java.util.Optional;

public interface LimitService {

    List<LimitDto.Response.FullInfo> findAllByAccountId(Long accountId);

    Optional<Limit> findNewestByAccountIdAndExpenseCategory(Long accountId, ExpenseCategoryType expenseCategoryType);

    Limit create(LimitDto.Request.Create requestDto);

}
