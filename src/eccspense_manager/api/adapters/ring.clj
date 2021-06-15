(ns eccspense-manager.api.adapters.ring
  (:require [integrant.core :as ig]
            [muuntaja.core :as m]
            [reitit.ring :as ring]
            [reitit.ring.middleware.parameters :as parameters]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [eccspense-manager.api.adapters.ring.routes :refer [routes]]
            [eccspense-manager.api.adapters.ring.util :refer [wrap-context]]))

(defn default-handler []
  (ring/create-default-handler
    {:not-found          (constantly {:status 404 :body "not found"})
     :method-not-allowed (constantly {:status 405 :body "method not allowed"})
     :not-acceptable     (constantly {:status 406 :body "not acceptable"})}))


(def ->router #(ring/router (routes) {:data {:muuntaja   m/instance
                                             :middleware [;; query-params & form-params
                                                          parameters/parameters-middleware
                                                          muuntaja/format-middleware
                                                          rrc/coerce-exceptions-middleware
                                                          rrc/coerce-request-middleware
                                                          rrc/coerce-response-middleware]}}))

(defn app-handler [system]
  (let [router (->router)]
    (ring/ring-handler router
                       (default-handler)
                       {:middleware [ (wrap-context system) ]})))

(comment
  (def app (app-handler {}))
  (app {:uri "/" :request-method :get}))

(defmethod ig/init-key ::handler [_ {:keys [profile] :as system}]
  (let [prod? (= :prod profile)]
    ;; Wrap in a function when not in prod.
    ;; This will recompile the router on every invocation which is a heavy performance penalty but will allow
    ;; to just evaluate handler functions without reloading the whole system which should be a better
    ;; developer experience overall.
    ;; Note this currently only works for synchronous ring handlers.
    ;; In prod we don't wrap and take advantage of reitit's pre-compiled route tree.
    (if prod? (app-handler system)
              (fn [req] ((app-handler system) req)))))
