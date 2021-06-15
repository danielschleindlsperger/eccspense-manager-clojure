(ns eccspense-manager.api.adapters.ring.routes
  (:require [reitit.coercion.malli]
            [malli.util :as mu]
            [ring.util.response :as response]
            [eccspense-manager.api.adapters.ring.util :refer [get-in-context]]
            [eccspense-manager.api.domain.repositories :as repo]
            [eccspense-manager.api.domain.model :as model]))

(defn get-transactions
  [req]
  {:status 200 :body (prn-str (repo/all-transactions! (get-in-context req :db) 10 []))})

(defn delete-transaction
  [req]
  (let [id (get-in req [:path-params :id])]
    (repo/delete-transaction! (get-in-context req :db) id)
    {:status 200 :body "success"}))

(defn save-transaction [req]
  (let [id (repo/save-transaction! (get-in-context req :db)
                                (-> req :parameters :body))]
    (response/created (str "/transaction/" id)
                      {:msg "transaction created"})))

(defn routes []
  [["/" {:get (constantly {:status 200 :body "TODO: Swagger docs"})}]
   ["/transaction" {:get  get-transactions
                    :post {:handler save-transaction
                           :coercion reitit.coercion.malli/coercion
                           :parameters {:body (mu/dissoc model/Transaction :id)}}
                    :put  save-transaction}]
   ["/transaction/:id" {:delete {:handler delete-transaction
                                 :coercion reitit.coercion.malli/coercion
                                 :parameters {:path [:map
                                                     [:id pos-int?]]}}}]])
