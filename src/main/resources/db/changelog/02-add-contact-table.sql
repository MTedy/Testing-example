CREATE TABLE contact (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255),
    address VARCHAR(255),
    phone_number VARCHAR(255),
    person_id BIGINT REFERENCES person(id) NOT NULL
);