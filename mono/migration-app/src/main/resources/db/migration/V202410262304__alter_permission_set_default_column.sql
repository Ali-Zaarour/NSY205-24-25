-- update table app_user_permission add active boolean default false;
alter table if exists app_user_permission
    add active boolean default false;