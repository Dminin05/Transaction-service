create table currency
(
    id            uuid    not null
        constraint currency_pk
            primary key,
    from_currency varchar not null,
    to_currency   varchar not null,
    value         numeric not null
);

alter table currency
    owner to postgres;

create table limits
(
    id               uuid                     not null
        constraint limits_pk
            primary key,
    account_id       bigint                   not null,
    expense_category varchar                  not null,
    month_limit      numeric                  not null,
    rest_of_limit    numeric                  not null,
    creation_date    timestamp with time zone not null,
    updated_date     timestamp with time zone not null,
    currency         varchar                  not null
);

alter table limits
    owner to postgres;

create table transactions
(
    id               uuid    not null
        constraint transactions_pk
            primary key,
    from_account     bigint  not null,
    to_account       bigint  not null,
    expense_category varchar not null,
    currency         varchar not null,
    date             date    not null,
    limit_exceeded   boolean not null,
    amount           numeric not null,
    limit_id         uuid
        constraint transactions_limits_id_fk
            references limits
);

alter table transactions
    owner to postgres;

