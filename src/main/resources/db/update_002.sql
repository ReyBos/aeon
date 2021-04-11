CREATE TABLE currency (
   id SERIAL PRIMARY KEY,
   name VARCHAR(50)
);

INSERT INTO currency(name) VALUES ('USD');

CREATE TABLE deposit (
   id SERIAL PRIMARY KEY,
   balance INT NOT NULL,
   currency_id INT REFERENCES currency(id) NOT NULL,
   person_id INT REFERENCES person(id) NOT NULL
);

CREATE TABLE deposit_transaction (
   id SERIAL PRIMARY KEY,
   sum INT NOT NULL,
   deposit_id INT REFERENCES deposit(id) NOT NULL
);