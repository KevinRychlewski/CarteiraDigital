CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    agencia VARCHAR(20) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    person_id BIGINT NOT NULL,
    saldo NUMERIC(19,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version INTEGER,

    CONSTRAINT uk_account_agencia_numero UNIQUE (agencia, numero),
    CONSTRAINT fk_account_client
        FOREIGN KEY (person_id)
        REFERENCES clients (id)
);
