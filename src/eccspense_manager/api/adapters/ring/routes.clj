(ns eccspense-manager.api.adapters.ring.routes
  (:require [eccspense-manager.api.adapters.ring.util :refer [get-in-context]]
            [eccspense-manager.api.domain.repositories :as repo]))

(defn get-transactions
  [req]
  {:status 200 :body (prn-str (repo/all-transactions! (get-in-context req :db) 10 []))})

(defn delete-transaction
  [req]
  (let [id (get-in req [:path-params :id])]
    (repo/delete-transaction! (get-in-context req :db) id)
    {:status 200 :body "success"}))

(defn save-transaction [req])

;; TODO: JSON encoding
;; TODO: parameter validation and coercion

(defn routes []
  [["/" {:get (constantly {:status 200 :body "TODO: Swagger docs"})}]
   ["/transaction" {:get  get-transactions
                    :post save-transaction
                    :put  save-transaction}]
   ["/transaction/:id" {:delete delete-transaction}]])
