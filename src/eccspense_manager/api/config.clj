(ns eccspense-manager.api.config
  (:require [aero.core :as aero]
            [clojure.java.io :as io]
            [integrant.core :as ig]))

(defn load-config
  [profile]
  (-> (io/resource "config.edn")
      (aero/read-config {:profile  profile
                         :resolver aero/resource-resolver})))

(defmethod ig/init-key ::config [_ {:keys [profile]}]
  (load-config profile))
