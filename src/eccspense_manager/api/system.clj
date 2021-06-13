(ns eccspense-manager.api.system
  "Defines the system's main (stateful) components and their inter-dependencies."
  (:require [integrant.core :as ig]
            [eccspense-manager.api.adapters.http-server]
            [eccspense-manager.api.adapters.ring]
            [eccspense-manager.api.adapters.db.sqlite]
            [eccspense-manager.api.adapters.db.duratom]
            [eccspense-manager.api.config]))


(def config :eccspense-manager.api.config/config)
(def http-server :eccspense-manager.api.adapters.http-server/http-server)
(def http-handler :eccspense-manager.api.adapters.ring/handler)
;(def db :eccspense-manager.api.adapters.db.sqlite/db)
;; Use in-memory implementation for prototyping
(def db :eccspense-manager.api.adapters.db.duratom/db)

(defn ->system-config [profile]
  {config       {:profile profile}
   db           {:config (ig/ref config)}
   http-handler {:profile profile
                 :config  (ig/ref config)
                 :db      (ig/ref db)}
   http-server  {:config  (ig/ref config)
                 :handler (ig/ref http-handler)}})

