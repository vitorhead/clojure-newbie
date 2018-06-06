(ns cljnewbiebank.handler
    (:use compojure.core)
    (:use ring.util.response)
    (:require [compojure.handler :as handler]
              [ring.middleware.json :as middleware]
              [compojure.route :as route]
              [cljnewbiebank.database :as db]))

(defn cljnewbiebank-response 
    "This function returns the content wrapped in a result tag"
    [content]
    (response {:result content}))

; ==
; Accounts endpoints
; ==

(defn get-accounts []
    (cljnewbiebank-response (db/get-all-accounts)))

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

; ==
; Users endpoints
; ==

(defn get-users []
    (cljnewbiebank-response (db/get-all-users)))

(defn get-user 
    [id]
    (cljnewbiebank-response (db/get-user (read-string id))))


; curl -d '{"name": "endpoint test", "email": "endpoint@endpoint.com"}' -H "Content-Type: application/json" -X POST localhost:8080/users
(defn post-user
    [body]
    (db/create-user body)
    {:status 201})

(defn put-user
    [id body]
    (db/update-user id body)
    (get-user id) ;return updated user
)

(defn delete-user
    [id]
    (db/delete-user id)
    {:status 204})

; == 
; Transactions endpoints
; ==
(defn get-transactions []
    (cljnewbiebank-response (db/get-all-transactions)))

(defn get-transaction 
    [id]
    (cljnewbiebank-response (db/get-transaction (read-string id))))


; curl -d '{"cdtransactiontype": 3, "amount": 25.0, "acc_to": 8, "acc_from": 11}' -H "Content-Type: application/json" -X POST localhost:8080/transactions
(defn post-transaction
    [body]
    (db/create-transaction body)
    {:status 201})

(defroutes cljnewbiebank-routes
  (context "/accounts" [] (defroutes accounts-routes
    (GET "/" [] (get-accounts))
    (POST "/" {body :body} (post-account body))
    (context "/:id" [id] (defroutes account-routes 
    ; :id is a placeholder and can then be injected into our parameter vector
      (GET "/" [] (get-account id)) 
      (PUT "/" {body :body} (put-account id body))
      (DELETE "/" [] (delete-account id))
    ))))
    
  (context "/users" [] (defroutes users-routes
    (GET "/" [] (get-users))
    (POST "/" {body :body} (post-user body))
    (context "/:id" [id] (defroutes user-routes
      (GET "/" [] (get-user id))
      (PUT "/" {body :body} (put-user id body))
      (DELETE "/" [] (delete-user id))
      ))))
  
  (context "/transactions" [] (defroutes transactions-routes
    (GET "/" [] (get-transactions))
    (POST "/" {body :body} (post-transaction body))
    (context "/:id" [id] (defroutes transaction-routes
      (GET "/" [] (get-transaction id))
      ))))
  
  (route/not-found "not found :(")
)
          
(def app
    (-> (handler/api cljnewbiebank-routes)
        (middleware/wrap-json-body) ;; parse json bodies
        (middleware/wrap-json-response))) ;; wrap responses in the ws