(ns eccspense-manager.api.domain.repositories)

(defprotocol UserRepository
  (get-user! [this id])
  (save-user! [this user]))

(defprotocol TransactionRepository
  (save-transaction! [this tx] "Insert or update a transaction and return its id.")
  (delete-transaction! [this id] "Delete a transaction and return its id.")
  (all-transactions! [this limit order-bys]))

(defprotocol CategoryRepository
  (save-category! [this category])
  (all-categories! [this limit order-bys]))
