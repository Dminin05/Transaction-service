package com.minin.transaction_service.model;

import com.minin.transaction_service.model.enums.ExpenseCategoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "from_account")
    private Long fromAccount;

    @Column(name = "to_account")
    private Long toAccount;

    @Column(name = "expense_category")
    @Enumerated(EnumType.STRING)
    private ExpenseCategoryType expenseCategory;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency.Type currency;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "date")
    private Date date;

    @Column(name = "limit_exceeded")
    private boolean limitExceeded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "limit_id")
    private Limit limit;

}
