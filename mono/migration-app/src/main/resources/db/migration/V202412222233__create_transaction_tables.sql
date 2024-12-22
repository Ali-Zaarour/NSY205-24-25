-- Create the transaction_type table
create table if not exists transaction_type
(
    id          uuid         not null default uuid_generate_v4(),
    key        varchar(50)  not null unique,
    created_at  timestamp    not null default now(),
    updated_at  timestamp,
    deleted_at  timestamp,

    constraint transaction_type_pkey primary key (id)
    );

comment on table transaction_type is 'Table for transaction types';

-- Create the status table
create table if not exists status
(
    id          uuid         not null default uuid_generate_v4(),
    key      varchar(50)  not null unique,
    created_at  timestamp    not null default now(),
    updated_at  timestamp,
    deleted_at  timestamp,

    constraint status_pkey primary key (id)
    );

comment on table status is 'Table for transaction statuses';

-- Create the transaction table
create table if not exists transactions
(
    id                  uuid         not null default uuid_generate_v4(),
    account_id          uuid         not null,
    amount              numeric(15, 2) not null,
    transaction_type_id uuid         not null,
    status_id           uuid         not null,
    created_at          timestamp    not null default now(),
    updated_at          timestamp,
    deleted_at          timestamp,

    constraint transaction_pkey primary key (id),
    constraint transaction_account_id_fkey foreign key (account_id) references account (id),
    constraint transaction_transaction_type_id_fkey foreign key (transaction_type_id) references transaction_type (id),
    constraint transaction_status_id_fkey foreign key (status_id) references status (id)
    );

comment on table transactions is 'Payment processing and history';