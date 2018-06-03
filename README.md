# cljnewbiebank

Intended to tackle on Clojure's idiomatic DB and REST, 
this repo was created for study matters, focusing on retrieving simple 
information from database and REST endpoints. 
It is not of my intention to deliver an awesome, full-powered and "production 
deployable" app.


## Models
The purpose of this section is to describe the tables that were used on the schema.
The data is maintained on postgresql-heroku.

They are very simple and straightforward. In a real app the tables should have
more fields: the `users` could hold more informations about the user 
(country, city, zip code), etc.
However, I just created the schema to have a structure in which I could test the
DB approaches and write some Clojure. 

### users
>iduser, name, createdat

### accounts
>idaccount, iduser, createdat, balance

## transactions_types
>cdtransactiontype, description

### transactions
>idtransaction, createdat, amount, acc_to, acc_from

An user can hold 1 (or more) accounts, those who can trade money by transactions.
Each transaction holds the amount, a holder for the type of the transaction 
and the accounts.
