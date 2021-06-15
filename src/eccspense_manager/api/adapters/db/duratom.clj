(ns eccspense-manager.api.adapters.db.duratom
  (:require [integrant.core :as ig]
            [duratom.core :refer [duratom]]
            [eccspense-manager.api.domain.repositories :refer [TransactionRepository]]))

;; simulate the "auto-increment" ids of sql databases
(def current-id (atom 0))
(defn next-id []
  (swap! current-id inc))


;; Each "table" is a (persistent) atom wrapping a map of id->entity
;; This means true transactional behaviour across multiple entities is not possible, but it's not really needed
;; for prototyping anyway.
(defrecord DuratomDB [transactions categories users]
  TransactionRepository
  (save-transaction! [this tx]
    (let [id (or (:id tx) (next-id))
          tx* (assoc tx :id id)]
      (swap! (:transactions this) assoc id tx*)
      id))

  (all-transactions [this _limit _order-bys]
    (or (vals @(:transactions this)) []))

  (delete-transaction! [this id]
    (swap! (:transactions this) dissoc id)
    id)

  (get-transaction [this id]
    (-> this :transactions deref (get id))))

(defmethod ig/init-key ::db [_ _]
  (map->DuratomDB {:transactions (duratom :local-file :commit-mode :sync :file-path "resources/db/transactions" :init {})
                   :categories   (duratom :local-file :commit-mode :sync :file-path "resources/db/categories" :init {})
                   :users        (duratom :local-file :commit-mode :sync :file-path "resources/db/users" :init {})}))