(ns eccspense-manager.api.domain.model
  (:require [malli.generator :as mg]))

(def Transaction
  [:map
   [:id uuid?]
   [:date inst?]
   [:amount decimal?]
   [:counter-party [:string {:max 200}]]
   [:descr [:string {:max 1000 :optional? true}]]
   [:category [:string {:max 100 :optional? true}]]])

(comment
  (mg/generate Transaction))