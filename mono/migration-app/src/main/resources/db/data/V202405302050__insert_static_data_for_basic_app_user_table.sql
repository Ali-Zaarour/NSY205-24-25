insert into app_user_permission (key, description)
values ('CREATE-USER', 'Permission to create a new user'),
       ('UPDATE-USER', 'Permission to update a user'),
       ('DELETE-USER', 'Permission to delete a user')
       --todo add more permissions here
ON CONFLICT DO NOTHING;

insert into app_user_role (key, description)
values ('LVL_0', 'client'),
       ('LVL_1','manager'),
       ('LVL_2', 'admin')
on conflict do nothing;

insert into app_user_status (key)
values ('ACTIVATED'),
       ('SUSPENDED'),
       ('DELETED') on conflict do nothing;