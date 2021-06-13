(ns user)

(set! *warn-on-reflection* true)

(println "Loaded namespace `user`, welcome to eccspense-manager!")
(println "Evaluate (dev) then (go) to start the application.")
(println "Evaluate (integrant.repl/reset) to reset after code changes")

(defn dev
  "Load 'dev' namespace and switch to it."
  []
  (require 'dev)
  (in-ns 'dev)
  :loaded)

(comment
  (dev))