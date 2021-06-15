(ns eccspense-manager.api.domain.model
  (:require [malli.generator :as mg]))

(def Transaction
  [:map
   [:id uuid?]
   [:date inst?]
   [:amount int?]
   [:counter-party [:string {:max 200}]]
   [:descr {:optional true} [:string {:max 1000}]]
   [:category {:optional true} [:string {:max 100}]]])

(comment
  (mg/generate Transaction))