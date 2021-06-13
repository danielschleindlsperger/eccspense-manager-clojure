(ns eccspense-manager.api.adapters.ring.util)

;;;;;;;;;;;;;
;; Context ;;
;;;;;;;;;;;;;

;(def ContextMap [:map])
;
;(defn validate-context-map!
;  "Validates that that the system context map conforms to the schema.
;   Throws when not."
;  [m]
;  (let [err (me/humanize (m/explain ContextMap m))]
;    (when err (throw (ex-info "System map does not conform to schema" err)))))

;; wrap dependencies
(defn wrap-context
  "Ring middleware to inject the system context map into the request map.
   This is a simple dependency injection mechanism that avoids having many higher order functions while
   still retaining the same testability."
  [context]
  (fn [handler]
    (fn [req] (handler (assoc req ::context context)))))

(defn get-in-context [req & path] (get-in req (concat [::context] path)))
