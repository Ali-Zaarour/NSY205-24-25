-- alter table app-user add column updated_by uuid;
alter table if exists app_user
    add column updated_by uuid,
    add constraint app_user_updated_by_fkey foreign key (updated_by) references app_user (id);
