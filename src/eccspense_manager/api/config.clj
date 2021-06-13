(ns eccspense-manager.api.config
  "Configuration that is passed to the application from the outside that is defined in resources/config.edn.
   This app tries to follow the 12-factor app methodology so it's mostly environment variables.
   The config can also differ based on the profile (local development or production)."
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
