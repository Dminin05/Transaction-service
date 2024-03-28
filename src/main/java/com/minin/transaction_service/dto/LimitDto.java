package com.minin.transaction_service.dto;

import com.minin.transaction_service.model.Currency;
import com.minin.transaction_service.model.enums.ExpenseCategoryType;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;

public enum LimitDto {;

    private interface AccountId { Long getAccountId(); }
    private interface ExpenseCategory { ExpenseCategoryType getExpenseCategory(); }
    private interface MonthLimit { BigDecimal getMonthLimit(); }
    private interface RestOfLimit { BigDecimal getRestOfLimit(); }
    private interface CreationDate { Date getCreationDate(); }
    private interface LimitCurrency { Currency.Type getCurrency(); }

    public enum Request {;

        @Value
        public static class Create implements AccountId, ExpenseCategory, MonthLimit {
            Long accountId;
            ExpenseCategoryType expenseCategory;
            BigDecimal monthLimit;
        }

    }


    public enum Response {;

        @Value
        public static class FullInfo implements AccountId, ExpenseCategory, MonthLimit, CreationDate, LimitCurrency, RestOfLimit {
            Long accountId;
            ExpenseCategoryType expenseCategory;
            BigDecimal monthLimit;
            Date creationDate;
            Currency.Type currency;
            BigDecimal restOfLimit;
        }

        @Value
        public static class BaseInfo implements MonthLimit, CreationDate, LimitCurrency {
            BigDecimal monthLimit;
            Date creationDate;
            Currency.Type currency;
        }

    }

}
