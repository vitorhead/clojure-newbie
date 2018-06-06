(ns cljnewbiebank.database-test
  (:require [clojure.test :refer :all]
            [cljnewbiebank.database :as db]))

; users table
(def test-user {:createdat (db/string-to-timestamp "2018-06-02 20:13:00") 
                :name "teste lein" 
                :email "foo@bar.foo"})
(def insert-user #(db/create-user %))

(defn create-user-test []
    (not= nil (insert-user test-user)))

(defn update-user-test []
    (let [dummy-user (first (db/get-all-users))
          dummy-id (:iduser dummy-user)]
        (= '(1) (db/update-user dummy-id {:name "database_test.update-user-test"}))
    )
)

(defn delete-user-test []
    (let [dummy-user (first (db/get-all-users))
          dummy-id (:iduser dummy-user)]
      (= '(1) (db/delete-user dummy-id))))
  

; accounts table
(def test-account {:iduser (:iduser (first (db/get-all-users)))
                   :createdat (db/string-to-timestamp "2018-06-02 20:13:00") 
                   :balance 500.00})
(def insert-account #(db/create-account %))

(defn create-account-test []
    (not= nil (insert-account test-account)))

(defn update-account-test []
    (let [dummy-account (first (db/get-all-accounts))
          dummy-id (:idaccount dummy-account)]
        (= '(1) (db/update-account dummy-id {:balance 450.0}))
    )
)

(defn delete-account-test []
    (let [dummy-account (first (db/get-all-accounts))
          dummy-id (:idaccount dummy-account)]
      (= '(1) (db/delete-account dummy-id))))
  
;transactions
(def test-transaction {:createdat (db/string-to-timestamp "2018-06-02 20:13:00") 
                       :cdtransactiontype 1 ; deposit
                       :amount 100
                       :acc_to (:idaccount (first (db/get-all-accounts)))})
(def insert-transaction #(db/create-transaction %))

(defn create-transaction-test []
    (not= nil (insert-transaction test-transaction)))

(deftest database-test
    (testing "INSERT on users" (is (create-user-test)))
    (testing "DELETE on users" (is (delete-user-test)))
    (testing "INSERT on users" (is (create-user-test)))
    (testing "UPDATE on users" (is (update-user-test)))
    (testing "INSERT on accounts" (is (create-account-test)))
    (testing "DELETE on accounts" (is (delete-account-test)))
    (testing "INSERT on accounts" (is (create-account-test)))
    (testing "UPDATE on accounts" (is (update-account-test)))   
    (testing "INSERT on transactions" (is (create-transaction-test)))
)
    
; lein test cljnewbiebank.database-test
