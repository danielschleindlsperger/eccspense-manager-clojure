(ns eccspense-manager.api.adapters.ring.routes
  (:require [reitit.coercion.malli]
            [malli.util :as mu]
            [ring.util.response :as response]
            [eccspense-manager.api.adapters.ring.util :refer [get-in-context]]
            [eccspense-manager.api.domain.repositories :as repo]
            [eccspense-manager.api.domain.model :as model]))

(defn get-transactions
  [req]
  (let [limit 10
        order-bys []]
    (response/response (repo/all-transactions (get-in-context req :db) limit order-bys))))

(defn get-transaction
  [req]
  (response/response (repo/get-transaction (get-in-context req :db)
                                           (-> req :parameters :path :id))))

(defn delete-transaction
  [req]
  (let [id (get-in req [:path-params :id])]
    (response/response {:msg     "success"
                        :deleted (repo/delete-transaction! (get-in-context req :db) id)})))

(defn save-transaction [req]
  (let [id (repo/save-transaction! (get-in-context req :db)
                                   (-> req :parameters :body))]
    (response/created (str "/transaction/" id)
                      {:msg "transaction created"})))

(defn get-categories
  [req]
  (let [limit 10
        order-bys []]
    (response/response (repo/all-categories (get-in-context req :db) limit order-bys))))

(defn get-category
  [req]
  (response/response (repo/get-category (get-in-context req :db)
                                        (-> req :parameters :path :id))))

(defn delete-category
  [req]
  (let [id (get-in req [:path-params :id])]
    (response/response {:msg     "success"
                        :deleted (repo/delete-category! (get-in-context req :db) id)})))

(defn save-category [req]
  (let [id (repo/save-category! (get-in-context req :db)
                                (-> req :parameters :body))]
    (response/created (str "/category/" id)
                      {:msg "category created"})))

(defn routes []
  [["/" {:get (constantly {:status 200 :body "TODO: Swagger docs"})}]
   ["/transaction" {:get  get-transactions
                    :post {:handler    save-transaction
                           :coercion   reitit.coercion.malli/coercion
                           :parameters {:body (mu/dissoc model/Transaction :id)}}
                    :put  save-transaction}]
   ["/transaction/:id" {:get    {:handler    get-transaction
                                 :coercion   reitit.coercion.malli/coercion
                                 :parameters {:path [:map
                                                     [:id pos-int?]]}}
                        :delete {:handler    delete-transaction
                                 :coercion   reitit.coercion.malli/coercion
                                 :parameters {:path [:map
                                                     [:id pos-int?]]}}}]
   ["/category" {:get  get-categories
                 :post {:handler    save-category
                        :coercion   reitit.coercion.malli/coercion
                        :parameters {:body (mu/dissoc model/Category :id)}}
                 :put  save-category}]
   ["/category/:id" {:get    {:handler    get-category
                              :coercion   reitit.coercion.malli/coercion
                              :parameters {:path [:map
                                                  [:id pos-int?]]}}
                     :delete {:handler    delete-category
                              :coercion   reitit.coercion.malli/coercion
                              :parameters {:path [:map
                                                  [:id pos-int?]]}}}]])
