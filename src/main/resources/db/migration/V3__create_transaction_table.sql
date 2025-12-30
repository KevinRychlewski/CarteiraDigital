CREATE TABLE transaction (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    counter_account_id BIGINT,
    transaction_type VARCHAR(20) NOT NULL,
    value NUMERIC(19,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    idempotency_key VARCHAR(100),
    created_at TIMESTAMP NOT NULL,
    processed_at TIMESTAMP,

    CONSTRAINT fk_transaction_account
        FOREIGN KEY (account_id)
        REFERENCES account (id),

    CONSTRAINT fk_transaction_counter_account
        FOREIGN KEY (counter_account_id)
        REFERENCES account (id)
);
