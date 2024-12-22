-- Insert default data into transaction_type table
insert into transaction_type (key)
values ('deposit'),
       ('withdrawal'),
       ('transfer'),
       ('payment') on conflict do nothing;

-- Insert default data into status table
insert into status (key)
values ('pending'),
       ('completed'),
       ('failed'),
       ('cancelled') on conflict do nothing;