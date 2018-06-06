(ns cljnewbiebank.database
    (:require [clojure.java.jdbc :as sql]
              [clojure.string :as str]
              [clj-time.coerce :as timec]
              [clj-time.format :as timef]
              [clj-time.core :as timecore]))
          

; Timestamp conversions
; These function can be used to convert a string into a timestamp and then insert it on the db
(defn string-to-timestamp
    [st]
    (timec/to-sql-date st))

(defn timestamp-to-string
    [ts]
    (timef/unparse (timef/formatters :date) (timec/from-sql-date ts)))

; DB Connection
; postgres://[user]:[password]@[host]:[port]/[database]
(def postgresql {:dbtype ""
                 :dbname ""
                 :host ""
                 :port ""
                 :user ""
                 :password ""
                 :ssl true
                 :sslfactory ""})                                  

; ==               
; Users table
; ==

; To convert the timestamp instance
(def user-from-db {:iduser identity,
                   :name identity, 
                   :createdat timestamp-to-string})

(defn create-user 
    [user]
    (let [parsed-user (into user 
                            {:createdat (timec/to-timestamp (timecore/now))}
                            )]
        (sql/insert! postgresql
                     :users parsed-user)))
           
(defn get-all-users []
    (into [] 
        (sql/query postgresql 
            ["SELECT * FROM users ORDER BY idUser DESC"])))
        
(defn get-user 
    [id]
    (sql/query postgresql 
              ["SELECT * FROM users WHERE idUser = ?" id])
)

(defn update-user
    [id user]
    (sql/update! postgresql :users user ["idUser = ?" id]))


(defn delete-user
    [id]
    (sql/delete! postgresql :users ["idUser = ?" id]))


; ==
; Accounts table
; ==

(defn create-account 
    [account]
    (let [parsed-account (into account 
                            {:createdat (timec/to-timestamp (timecore/now))}
                            )]    
        (sql/insert! postgresql
                     :accounts parsed-account)))
           
(defn get-all-accounts []
    (into [] 
        (sql/query postgresql 
            ["SELECT * FROM accounts ORDER BY idAccount DESC"])))
        
(defn get-account 
    [id]
    (sql/query postgresql 
              ["SELECT * FROM accounts WHERE idAccount = ?" id])
)

(defn update-account
    [id account]
    (sql/update! postgresql :accounts account ["idAccount = ?" id]))


(defn delete-account
    [id]
    (sql/delete! postgresql :accounts ["idAccount = ?" id]))

; ==
; Transactions table
; ==

(defn create-transaction 
    [transaction]
    (let [parsed-transaction (into transaction 
                              {:createdat (timec/to-timestamp (timecore/now))}
                             )]      
      (sql/insert! postgresql
                   :transactions parsed-transaction)))
           
(defn get-all-transactions []
    (into [] 
        (sql/query postgresql 
            ["SELECT * FROM transactions ORDER BY idTransaction DESC"])))
        
(defn get-transaction 
    [id]
    (sql/query postgresql 
              ["SELECT * FROM transactions WHERE idTransaction = ?" id])
)
