
CREATE TABLE users (
  idUser SERIAL PRIMARY KEY NOT NULL,  
  createdAt TIMESTAMP NOT NULL,
  name VARCHAR(150) NOT NULL,
  email VARCHAR(100) NOT NULL
);

CREATE TABLE accounts (
    idAccount SERIAL PRIMARY KEY NOT NULL,
    idUser INT NOT NULL REFERENCES users (idUser),
    createdAt TIMESTAMP NOT NULL,
    balance DECIMAL(10,6) NOT NULL
);

CREATE TABLE transactions_types (
    cdTransactionType INT PRIMARY KEY NOT NULL,
    description VARCHAR(100) NOT NULL
);

CREATE TABLE transactions (
   idTransaction SERIAL PRIMARY KEY NOT NULL,
   createdAt TIMESTAMP NOT NULL,
   cdTransactionType INT NOT NULL REFERENCES transactions_types (cdTransactionType),
   amount DECIMAL(10, 6) NOT NULL,
   acc_to INT NULL REFERENCES accounts (idAccount),
   acc_from INT NULL REFERENCES accounts (idAccount)
);

/*
    INSERT INTO transactions_types
        (cdTransactionType, description)
    VALUES
        (1, 'deposit'),
        (2, 'withdraw'),
        (3, 'transfer')
*/



