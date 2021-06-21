(ns eccspense-manager.api.domain.repositories)

(defprotocol UserRepository
  (get-user [this id])
  (save-user! [this user]))

(defprotocol TransactionRepository
  (all-transactions [this limit order-bys])
  (get-transaction [this id])
  (save-transaction! [this tx] "Insert or update a transaction and return its id.")
  (delete-transaction! [this id] "Delete a transaction and return its id."))

(defprotocol CategoryRepository
  (all-categories [this limit order-bys])
  (get-category [this id])
  (save-category! [this category])
  (delete-category! [this id]))
