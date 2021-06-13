(ns dev
  (:require [taoensso.timbre]
            [integrant.repl :refer [clear go halt prep init reset reset-all]]
            [eccspense-manager.api.system :refer [->system-config]]))

(integrant.repl/set-prep! (constantly (->system-config :dev)))

(comment
  (go))

(comment
  (reset)
  (halt))
