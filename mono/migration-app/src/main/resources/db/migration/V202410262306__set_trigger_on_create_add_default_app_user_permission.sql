-- Function to add active permissions to a new user
-- Create the trigger function
CREATE OR REPLACE FUNCTION add_active_permissions_to_new_user()
    RETURNS TRIGGER AS $$
DECLARE
    m0_user_id UUID;
BEGIN
    -- Get the user ID where organization ID is the key 'M0'
    SELECT id INTO m0_user_id
    FROM app_user
    WHERE username = 'm0_admin'
    LIMIT 1;

    -- Insert all active permissions for the new user into app_user_permission_mapping
    INSERT INTO app_user_permission_mapping (au_id, aup_id, created_by)
    SELECT NEW.id, aup.id, m0_user_id
    FROM app_user_permission aup
    WHERE aup.active = true;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create the trigger
CREATE TRIGGER after_insert_app_user
    AFTER INSERT ON app_user
    FOR EACH ROW
EXECUTE FUNCTION add_active_permissions_to_new_user();