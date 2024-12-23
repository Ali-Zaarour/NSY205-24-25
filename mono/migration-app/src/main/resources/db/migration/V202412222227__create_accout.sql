create table if not exists account
(
    id              uuid         not null default uuid_generate_v4(),
    user_id         uuid         not null,
    account_number  varchar(50)  not null unique,
    balance         double precision not null default 0.00,
    created_at      timestamp    not null default now(),
    updated_at      timestamp,
    deleted_at      timestamp,

    constraint account_pkey primary key (id),
    constraint account_user_id_fkey foreign key (user_id) references app_user (id)
    );

comment on table account is 'User account details and balances';