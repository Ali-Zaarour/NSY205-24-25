DO
$$
    DECLARE
        i          INT;
        j          INT;
        k          INT;
        user_id    UUID;
        account_id UUID;
        status_ids UUID[];
    BEGIN
        -- Get all status IDs
        SELECT array_agg(id) INTO status_ids FROM status;

        FOR i IN 1..10000 LOOP
                -- Insert user
                INSERT INTO app_user (username, password, os_id)
                VALUES (
                           'user_' || i,
                           '$argon2id$v=19$m=16384,t=2,p=1$1DJSYnC4Fb8PR7R/rfQZ6w$Uz6MKw+/GI6ttmp20j3QrjdqrczC7N6xOqIV2dCUp3I',
                           (SELECT id FROM organization_side WHERE key = 'M0')
                       )
                RETURNING id INTO user_id;

                FOR j IN 1..2 LOOP
                        -- Insert account
                        INSERT INTO account (user_id, account_number, balance)
                        VALUES (
                                   user_id,
                                   'account_' || i || '_' || j,
                                   round((random() * 10000)::numeric, 2) -- Random balance between 0 and 10000
                               )
                        RETURNING id INTO account_id;

                        FOR k IN 1..10 LOOP
                                -- Insert transaction
                                INSERT INTO transactions (account_id, amount, transaction_type_id, status_id)
                                VALUES (
                                           account_id,
                                           round((random() * 1000)::numeric, 2),
                                           (SELECT id FROM transaction_type ORDER BY random() LIMIT 1),
                                           status_ids[(k % array_length(status_ids, 1)) + 1]
                                       );
                            END LOOP;
                    END LOOP;
            END LOOP;
    END;
$$;