\c powerpal_database;

GRANT ALL PRIVILEGES ON TABLE public.users TO taskmanager_user;


INSERT INTO public.users (
    user_type, id, address, email, first_name, last_name, password, phone_number, username, status, is_password_changed, profile_image_id
)
VALUES
    ('Citizen', 'e1a6cfd5-3e6e-48a9-9a93-f2b5d2dff000', '123 Main St', 'owner1@example.com', 'John', 'Doe', '$2a$10$qhvH7DZus4ot96VzoIy/je3c2GQ6107o5ui5AsdM14q3zEaEvIz2G', '555-1234', 'owner1', NULL, FALSE, NULL),
    ('Citizen', 'd2e88bcb-bc34-4c1f-8ff3-ffe8f1b3cc23', '456 Elm St', 'owner2@example.com', 'Jane', 'Smith', '$2a$10$qhvH7DZus4ot96VzoIy/je3c2GQ6107o5ui5AsdM14q3zEaEvIz2G', '555-5678', 'owner2', NULL, FALSE, NULL);
