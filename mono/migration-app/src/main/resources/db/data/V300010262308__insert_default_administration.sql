-- todo this file need to be in the top always

--insert admin app_user default administrator data
insert into app_user(username, password, os_id, aur_id)
values ('m0_admin', '$argon2id$v=19$m=16384,t=2,p=1$1DJSYnC4Fb8PR7R/rfQZ6w$Uz6MKw+/GI6ttmp20j3QrjdqrczC7N6xOqIV2dCUp3I',
        (select id from organization_side where key = 'M0'), (select id from app_user_role where key = 'LVL_2')),
       ('m0_user', '$argon2id$v=19$m=16384,t=2,p=1$1DJSYnC4Fb8PR7R/rfQZ6w$Uz6MKw+/GI6ttmp20j3QrjdqrczC7N6xOqIV2dCUp3I',
        (select id from organization_side where key = 'M0'), (select id from app_user_role where key = 'LVL_2'))
on conflict do nothing;

-- create a function to add all the list of permissions to an administrator
CREATE OR REPLACE FUNCTION add_all_permissions_to_admin()
    RETURNS void AS
$$
DECLARE
    admin_id UUID;
BEGIN
    -- Get the admin ID
    SELECT id
    INTO admin_id
    FROM app_user
    WHERE username = 'm0_admin'
    LIMIT 1;

    IF admin_id IS NOT NULL THEN
        -- Insert all permissions for the admin into app_user_permission_mapping
        INSERT INTO app_user_permission_mapping (au_id, aup_id, created_by)
        SELECT admin_id, aup.id, admin_id
        FROM app_user_permission aup
        ON CONFLICT (au_id, aup_id) DO NOTHING;
    END IF;

END;
$$ LANGUAGE plpgsql;

-- Call the function to add all permissions to the admin user
SELECT add_all_permissions_to_admin();