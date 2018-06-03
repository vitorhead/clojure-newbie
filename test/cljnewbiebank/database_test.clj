(ns cljnewbiebank.database-test
  (:require [clojure.test :refer :all]
            [cljnewbiebank.database :as db]))

(def test-user {:createdat (db/string-to-timestamp "2018-06-02 20:13:00") :name "teste lein" :email "foo@bar.foo"})
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

(deftest database-test
    (testing "INSERT on users" (is (create-user-test)))
    (testing "DELETE on users" (is (delete-user-test)))
    (testing "INSERT on users" (is (create-user-test)))
    (testing "UPDATE on users" (is (update-user-test)))
)
    
; lein test cljnewbiebank.database-test
