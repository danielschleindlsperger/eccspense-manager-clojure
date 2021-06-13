(ns eccspense-manager.api.domain.repositories)

(defprotocol UserRepository
  (get-user! [this id])
  (save-user! [this user]))

(defprotocol TransactionRepository
  (save-transaction! [this tx])
  (delete-transaction! [this id])
  (all-transactions! [this limit order-bys]))

(defprotocol CategoryRepository
  (save-category! [this category])
  (all-categories! [this limit order-bys]))
