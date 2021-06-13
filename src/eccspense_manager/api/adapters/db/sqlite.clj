(ns eccspense-manager.api.adapters.db.sqlite
  (:require [integrant.core :as ig]
            [next.jdbc :as jdbc]
            [next.jdbc.protocols :refer [Executable]]
            [eccspense-manager.api.domain.repositories :as repo])
  (:import (org.sqlite.jdbc4 JDBC4Connection)))

;(extend-protocol TestRepository
;  JDBC4Connection
;  (sum [db a b]
;    (:sum (jdbc/with-transaction [tx db]
;                                 (jdbc/execute-one! tx ["select ? + ? as sum" a b])))))

(defmethod ig/init-key ::db [_ {:keys [config] :as system}]
  (jdbc/get-connection {:dbtype "sqlite" :dbname (:db-url config)}))


(defmethod ig/halt-key! ::db [_ db]
  (when db (.close db)))
