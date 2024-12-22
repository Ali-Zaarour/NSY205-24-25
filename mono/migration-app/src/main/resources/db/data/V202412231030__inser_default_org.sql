-- insert admin organization_side default data
insert into organization_side (key, description)
values ('M0','M0')
    on conflict do nothing;