(ns cljnewbiebank.context.accounts
    (:use ring.util.response)
    (:require [cljnewbiebank.database :as db]
              [clj-time.coerce :as timec]
              [clj-time.format :as timef]
              [clj-time.core :as timecore]
              [clojure.java.jdbc :as sql]))
          
; ==
; Accounts table
; ==

(defn insert-account 
    [account]
    (let [parsed-account (into account 
                            {:createdat (timec/to-timestamp (timecore/now))}
                            )]    
        (sql/insert! db/postgresql
                     :accounts parsed-account)))
           
(defn select-all-accounts []
    (into [] 
        (sql/query db/postgresql 
            ["SELECT * FROM accounts ORDER BY idAccount DESC"])))
        
(defn select-account 
    [id]
    (sql/query db/postgresql 
              ["SELECT * FROM accounts WHERE idAccount = ?" id])
)

(defn update-account
    [id account]
    (sql/update! db/postgresql :accounts account ["idAccount = ?" id]))


(defn delete-account
    [id]
    (sql/delete! db/postgresql :accounts ["idAccount = ?" id]))

          
          
; == 
; Accounts endpoint
; ==
(defn get-accounts []
    (cljnewbiebank-response (select-all-accounts)))

(defn get-account 
    [id]
    (cljnewbiebank-response (db/get-account (read-string id))))


; curl -d '{"iduser": 35, "balance": 50.0}' -H "Content-Type: application/json" -X POST localhost:8080/accounts
(defn post-account
    [body]
    (db/create-account body)
    {:status 201})

(defn put-account
    [id body]
    (db/update-account id body)
    (get-account id) ;return updated account
)

(defn delete-account
    [id]
    (db/delete-account id)
    {:status 204})