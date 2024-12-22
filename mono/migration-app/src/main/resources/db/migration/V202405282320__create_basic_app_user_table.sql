create table if not exists app_user_status
(
    id         uuid        not null default uuid_generate_v4(),
    key        varchar(50) not null,
    created_at timestamp   not null default now(),
    updated_at timestamp,
    deleted_at timestamp,

    constraint app_user_status_pkey primary key (id),
    constraint app_user_status_key_key unique (key)
);
comment on table app_user_status is 'This table provide the user/account status';

--@doc     Create a function to get the status_id based on the key, this will be used
--         in the app_user table ta assign the default status.
CREATE OR REPLACE FUNCTION get_default_app_user_status_id()
    RETURNS UUID AS
$$
DECLARE
    status_id UUID;
BEGIN
    SELECT id INTO status_id FROM app_user_status WHERE key = 'ACTIVATED' LIMIT 1;
    RETURN status_id;
END;
$$ LANGUAGE plpgsql;

comment on function get_default_app_user_status_id() is 'Get default user status id';

-------------------------------------------------------------------------------------------

create table if not exists app_user_role
(
    id          uuid        not null default uuid_generate_v4(),
    key         varchar(50) not null,
    description varchar(200),
    created_at  timestamp   not null default now(),
    updated_at  timestamp,
    deleted_at  timestamp,

    constraint app_user_role_pkey primary key (id),
    constraint app_user_role_key_key unique (key)
);
comment on table app_user_role is ' This table stock different app user role type';

--@doc     Create a function to get the role_id based on the key, this will be used
--         in the app_user table ta assign the default role.
CREATE OR REPLACE FUNCTION get_default_app_user_role_id()
    RETURNS UUID AS
$$
DECLARE
    role_id UUID;
BEGIN
    SELECT id INTO role_id FROM app_user_role WHERE key = 'LVL_0' LIMIT 1;
    RETURN role_id;
END;
$$ LANGUAGE plpgsql;

comment on function get_default_app_user_role_id() is 'Get user default role id';

-------------------------------------------------------------------------------------------

create table if not exists app_user_permission
(
    id          uuid        not null default uuid_generate_v4(),
    key         varchar(50) not null,
    description varchar(200),
    created_at  timestamp   not null default now(),
    updated_at  timestamp,
    deleted_at  timestamp,

    constraint app_user_permission_pkey primary key (id),
    constraint app_user_permission_key_key unique (key)
);

comment on table app_user_permission is 'This table stock different app user permission type';

-------------------------------------------------------------------------------------------

create table if not exists organization_side
(
    id           uuid        not null default uuid_generate_v4(),
    key          varchar(50) not null,
    description  varchar(200),
    os_parent_id uuid,
    created_at   timestamp   not null default now(),
    updated_at   timestamp,
    deleted_at   timestamp,

    constraint organization_side_pkey primary key (id),
    constraint organization_side_key_key unique (key),
    constraint organization_side_self_fkey foreign key (os_parent_id) references organization_side (id)
);

comment on table organization_side is 'Organization recursive schema to provide user with any position from any level';

-------------------------------------------------------------------------------------------

create table if not exists app_user
(
    id              uuid         not null default uuid_generate_v4(),
    username        varchar(200) not null,
    password        varchar(115),
    psw_last_update timestamp    not null default now(),
    os_id           uuid         not null,
    aus_id          uuid         not null default get_default_app_user_status_id(),
    aur_id          uuid         not null default get_default_app_user_role_id(),
    login_attempts  int                   default 0,
    last_login_at   timestamp,
    created_at      timestamp    not null default now(),
    updated_at      timestamp,
    deleted_at      timestamp,

    constraint app_user_pkey primary key (id),
    constraint app_user_unique_username unique (username),
    constraint app_user_aus_id_fkey foreign key (aus_id) references app_user_status (id),
    constraint app_user_aur_id_fkey foreign key (aur_id) references app_user_role (id),
    constraint app_user_os_id_fkey foreign key (os_id) references organization_side (id)

);
comment on table app_user is 'The app user contain all user information to be user in auth action';

-------------------------------------------------------------------------------------------
create table if not exists app_user_permission_mapping
(
    au_id      uuid,
    aup_id     uuid,
    created_at timestamp not null default now(),
    updated_at timestamp,
    deleted_at timestamp,
    created_by uuid      not null,

    constraint app_user_permission_mapping_pkey primary key (au_id, aup_id),
    constraint app_user_permission_mapping_au_id_fkey foreign key (au_id) references app_user (id),
    constraint app_user_permission_mapping_created_by_fkey foreign key (created_by) references app_user (id),
    constraint app_user_permission_mapping_aup_id_fkey foreign key (aup_id) references app_user_permission (id)
);

comment on table app_user_permission_mapping is 'Map user permission for different operation';

-------------------------------------------------------------------------------------------