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
@Table(name = "limits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "expense_category")
    @Enumerated(EnumType.STRING)
    private ExpenseCategoryType expenseCategory;

    @Column(name = "month_limit")
    private BigDecimal monthLimit;

    @Column(name = "rest_of_limit")
    private BigDecimal restOfLimit;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "currency")
    private Currency.Type currency;

}
