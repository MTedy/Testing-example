CREATE TABLE contact (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    person_id BIGINT REFERENCES person(id) NOT NULL
);