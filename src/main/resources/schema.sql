CREATE TABLE IF NOT EXISTS Person (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    second_name VARCHAR(50),
    addresses VARCHAR(255),
    date_of_birth DATE,
    phone_numbers VARCHAR(255),
    personal_photo BLOB
);

CREATE TABLE IF NOT EXISTS Address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    addresses VARCHAR(255)
);