(ns reagent.core
  (:refer-clojure :exclude [atom]))

;; these are utilities to support server side rendering with react/reagent

(def atom clojure.core/atom)

(defn cursor
  "Reactive cursor placeholder for server-side rendering."
  [state path]
  (atom (get-in @state path)))
