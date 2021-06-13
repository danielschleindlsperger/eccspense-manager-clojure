(ns eccspense-manager.api.main
  (:require [integrant.core :as ig]
            [taoensso.timbre :as log]
            [eccspense-manager.api.system :refer [->system-config]])
  (:gen-class))

(defn -main
  [& _args]
  (log/info "Bootstrapping application...")
  (let [system (ig/init (->system-config :prod))]
    (-> (Runtime/getRuntime)
        (.addShutdownHook (Thread. ^Runnable (fn []
                                               (log/info "Shutting down system")
                                               (ig/halt! system)))))
    (log/info "System running")))