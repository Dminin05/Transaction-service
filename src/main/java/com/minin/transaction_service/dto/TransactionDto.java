package com.minin.transaction_service.dto;

import com.minin.transaction_service.model.Currency;
import com.minin.transaction_service.model.enums.ExpenseCategoryType;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;

public enum TransactionDto {;

    private interface FromAccount { Long getFromAccount(); }
    private interface ToAccount { Long getToAccount(); }
    private interface TransactionCurrency { Currency.Type getCurrency(); }
    private interface ExpenseCategory { ExpenseCategoryType getExpenseCategory(); }
    private interface Datetime { Date getDate(); }
    private interface Amount { BigDecimal getAmount(); }
    private interface Limit { LimitDto.Response.BaseInfo getLimit(); }

    public enum Request {;

        @Value
        public static class Create implements FromAccount, ToAccount, TransactionCurrency, ExpenseCategory, Datetime, Amount {
            Long fromAccount;
            Long toAccount;
            Currency.Type currency;
            ExpenseCategoryType expenseCategory;
            Date date;
            BigDecimal amount;
        }

    }

    public enum Response {;

        @Value
        public static class Info implements FromAccount, ToAccount, TransactionCurrency, ExpenseCategory, Datetime, Amount, Limit {
            Long fromAccount;
            Long toAccount;
            Currency.Type currency;
            ExpenseCategoryType expenseCategory;
            Date date;
            BigDecimal amount;
            LimitDto.Response.BaseInfo limit;
        }

    }

}
